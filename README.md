# RPC代码

## 项目结构

### module

- **rpc-commo**: 公共代码
- **rpc-consume**: RPC消费者
- **rpc-provider**: RPC服务端

### 工程模块

- **rpc-sample-provider**: 服务发布模块
- **rpc-sample-api**: provider facade包模块
- **rpc-sample-consumer**: 服务消费spring cloud工程

## 依赖项

- java17
- gradle
- zookeeper

## 本地部署

1. zookeeper
   ```shell
   cd apache-zookeeper-3.7.1-bin/bin
   ./zkCli.sh -server 127.0.0.1:2181
   ```
2. 启动rpc-server
    1. 发布服务
3. 启动rpc-consumer
    1. 调用服务
