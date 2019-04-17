package core;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import utils.DataUtils;

import java.util.Collections;
import java.util.List;

public class MysqlPipeline implements Pipeline {

    @Override
    @SuppressWarnings("unchecked")
    public void process(ResultItems resultItems, Task task) {
        resultItems.getAll().forEach((k, v) -> {
            if(k.contains("list")){
                saveList((List<Object>) v);
            }else{
                saveList(Collections.singletonList(v));
            }
        });
    }


    private void saveList(List<Object> list){
        list.forEach(item ->{
            try {
                DataUtils.save(item);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


}
