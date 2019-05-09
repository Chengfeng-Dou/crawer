package utils;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class DataUtils {
    private static SessionFactory factory;
    private static BlockingQueue<Object> blockingQueue = new LinkedBlockingQueue<>();
    private static SaveTask task = new SaveTask();

    static {
        Configuration configuration = new Configuration();
        configuration.configure();
        factory = configuration.buildSessionFactory();
        new Thread(task).start();
    }


    public static void save(Object object) throws InterruptedException {
        blockingQueue.put(object);
    }


    public static void close() {
        task.close();
        factory.close();
    }

    @SuppressWarnings("unchecked")
    public static List<String> getCityList(){
        Session session = factory.openSession();
        Query query = session.createQuery(
                "select link from model.CityInfo"
        );
        List result = query.list();
        List<String> ret = new LinkedList<>();
        result.forEach(v-> ret.add((String) v));

        return ret;
    }


    private static class SaveTask implements Runnable {
        boolean flush = false;
        boolean close = false;

        @Override
        public void run() {
            while (!close) {
                if (blockingQueue.size() > 10 || flush) {
                    try{
                        Session session = factory.openSession();
                        Transaction beginTransaction = session.beginTransaction();

                        int size = blockingQueue.size();
                        for (int i = 0; i < size; i++) {
                            Object o = blockingQueue.poll();
                            session.saveOrUpdate(o);
                        }

                        beginTransaction.commit();
                        session.close();

                        flush = false;
                    }catch (JDBCException e){
                        System.out.println("this batch has been saved!");
                    }

                }

                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("DB connection closed");
        }

        void close() {
            this.flush = true;
            while (!blockingQueue.isEmpty()) {
                Thread.yield();
            }
            this.close = true;
        }
    }
}
