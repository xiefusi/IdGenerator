quick start
该jar包依赖springboot，只能在有数据源的模块中使用
如果使用模块中有spring管理的数据源可以不用配置数据源
如果没有需要手动添加数据源
服务器不同实例是通过数据库的主键生成表来确保id生成唯一的
```xml
<dependency>
    <groupId>com.github.xiefusi</groupId>
    <artifactId>key-generator-starter</artifactId>
    <version>${id.generator.version}</version>
</dependency>
```
```yaml
key-generator:
  dbcust: true #默认为false(使用使用模块中的连接池)
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://${MYSQL_HOST:localhost}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:id-generator}?useUnicode=true&characterEncoding=UTF8&useSSL=false
    username: ${MYSQL_USERNAME:root}
    password: ${MYSQL_PASSWORD:root}
  businesses:
    - business-id: orderId #业务场景（必填）
      begin: 1 #id开始自增点（非必填默认1L）
      step: 5000 #自增步长（非必填默认5000）
      description: 订单id #业务场景秒速（非必填默认null）
```
```java
Result result = IdUtil.get("orderId");
if (Status.SUCCESS.equals(result.getStatus())) {
    long orderId = result.getId();
}
```
