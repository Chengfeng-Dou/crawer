# tlosp的爬虫
## 简介
该爬虫使用Java语言编写而成，基于webmagic和hibernate。该爬虫具有断点续爬，添加动态代理和数据库事务等功能，暂不支持分布式。
## 如何安装
该爬虫基于maven构建，需要使用带有maven插件的IDE进行构建，推荐使用IDEA。爬虫可导入IDEA进行运行，亦可达成jar包部署到服务器上（推荐，不占用本地资源）。
## 如何添加自己的功能
### 修改数据库
配置文件位于src/main/resources/hibernate.cfg.xml，这个XML文件的每一行都有注释，如何修改数据库参照注释即可
### 编写自己的提取器
提取器分别为两个部分，一个时extractor，位于extractors文件夹下面，一个时model，位于model文件夹下面。extractor需要实现PageExtractor接口，以及使用@TargetUrl注解来表明时哪一个网站的解析器。model的编写方法请参见网上的hibernate的教程。
### 配置动态代理
动态代理位于utils包下面的IPUtil，修改getNewIP方法即可，我这里的动态代理是通过访问url来获取IP地址，如果你的动态代理访问方式和我的相同，则只用修改url即可，否则需要全部修改
