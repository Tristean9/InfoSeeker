# InfoSeeker

InfoSeeker 是一个B/S模式的信息检索系统，基于Lucene和ElasticSearch实现。该系统能够动态追踪解析XML文档（联邦公报），并存储到数据库。通过调用百度或Google翻译API，系统会生成文档的中英文两个版本，并实现中英文全文检索功能。

## 功能介绍

- **动态XML解析**: 实时追踪和解析联邦公报中的XML文档，并解析存储。
- **数据库存储**: 将解析后的文档存储在数据库中，方便后续检索和分析。
- **自动翻译**: 调用外部翻译API，提供文档内容的中英文两个版本。
- **全文检索**: 结合Lucene或ElasticSearch实现强大的中英文全文检索功能。

## 项目结构

```markdown
InfoSeeker:
│   README.md                          # 项目说明文档
│   xml_db.sql                         # 数据库初始化脚本
│   
├───frontend                           # 前端目录
│   │   .gitignore                     # 前端git忽略文件
│   │   index.html                     # 前端HTML入口文件
│   │   jsconfig.json                  # JS配置
│   │   package-lock.json              # 锁定安装的npm包版本
│   │   package.json                   # npm包和脚本配置
│   │   README.md                      # 前端README文件
│   │   vite.config.js                 # Vite配置文件
│   │   
│   ├───src                            # 前端源代码目录
│   │   │   App.vue                    # 主Vue应用文件
│   │   │   main.js                    # Vue应用的入口JS文件
│   │   │   
│   │   ├───assets                     # 静态资源，如样式和图片
│   │   │       base.css               # 基础样式文件
│   │   │       logo.svg               # 应用Logo
│   │   │       main.css               # 主样式文件
│   │   │       
│   │   ├───components                 # Vue组件目录
│   │   │       ...                    # 各种Vue组件文件
│   │   │       
│   │   ├───router                     # Vue路由配置目录
│   │   │       index.js               # 路由配置文件
│   │   │       
│   │   ├───store                      # Vuex状态管理目录
│   │   │       index.js               # Vuex主文件
│   │   │       
│   │   └───views                      # Vue视图/页面目录
│   │           ...                    # 各种页面组件文件
│   │           
│   └───public                         # 公共静态文件
│           favicon.ico                # 网站图标
│           
└───Backend                        # 后端目录（Spring Boot项目）
    ├───src                            # 后端源代码目录
    │   ├───main                       # 主应用源代码目录
    │   │   ├───java                   # Java源代码目录
    │   │   │   └───com
    │   │   │       └───zth
    │   │   │           └───infoseeker
    │   │   │               │   InfoSeekerApplication.java # Spring Boot应用的入口点
    │   │   │               │   
    │   │   │               ├───config                     # 配置类目录
    │   │   │               │       WebSecurityConfig.java # 安全配置，如认证和权限规则
    │   │   │               │       
    │   │   │               ├───controller                 # 控制器目录
    │   │   │               │       AdminController.java   # 处理管理员相关的Web请求
    │   │   │               │       AuthController.java    # 处理身份验证相关的Web请求
    │   │   │               │       UserController.java    # 处理用户相关的Web请求
    │   │   │               │       
    │   │   │               ├───dto                        # 数据传输对象（DTO）目录
    │   │   │               │       ParsedDocumentDTO.java # 封装解析文档数据的DTO
    │   │   │               │       
    │   │   │               ├───exception                  # 自定义异常目录
    │   │   │               │       AuthenticationException.java # 自定义认证相关异常
    │   │   │               │       
    │   │   │               ├───integration                # 外部服务集成目录
    │   │   │               │       BaiduTranslateAPI.java # 百度翻译API集成
    │   │   │               │       
    │   │   │               ├───model                      # 实体模型目录
    │   │   │               │       ParsedDocument.java    # 解析文档的模型定义
    │   │   │               │       RawDocument.java       # 原始文档的模型定义
    │   │   │               │       User.java              # 用户模型定义
    │   │   │               │       
    │   │   │               ├───repository                 # 数据库访问层目录
    │   │   │               │       ParsedDocumentRepository.java # 解析文档的JPA仓库
    │   │   │               │       RawDocumentRepository.java    # 原始文档的JPA仓库
    │   │   │               │       
    │   │   │               └───service                    # 业务逻辑服务层目录
    │   │   │                   ├───impl                   # 服务接口实现目录
    │   │   │                   │       AuthServiceImpl.java       # 身份验证服务实现
    │   │   │                   │       CrawlerServiceImpl.java    # 爬虫服务实现
    │   │   │                   │       DataProcessingServiceImpl.java # 数据处理服务实现
    │   │   │                   │       IndexServiceImpl.java      # 索引服务实现
    │   │   │                   │       SearchServiceImpl.java     # 搜索服务实现
    │   │   │                   │       TranslationServiceImpl.java # 翻译服务实现
    │   │   │                   │       XmlParserServiceImpl.java   # XML解析服务实现
    │   │   │                   │       
    │   │   │                   └───interfaces              # 服务接口目录
    │   │   │                           AuthService.java             # 身份验证服务接口
    │   │   │                           ICrawlerService.java         # 爬虫服务接口
    │   │   │                           IDataProcessingService.java  # 数据处理服务接口
    │   │   │                           IIndexService.java           # 索引服务接口
    │   │   │                           ISearchOne.java              # 单一搜索服务接口
    │   │   │                           ISearchService.java          # 搜索服务接口
    │   │   │                           ITranslationService.java     # 翻译服务接口
    │   │   │                           IXmlParserService.java       # XML解析服务接口
    │   │   │                           
    │   │   └───resources              # 资源目录，包含配置文件等
    │   │           application.properties # Spring Boot配置文件
    │   └───test                       # 测试源代码目录
    │       └───...                    # 测试相关文件
    └───target                         # 编译生成的文件和目录
```
## 运行说明

### 前提条件

在运行项目之前，请确保你的系统中安装了以下软件：

- [Node.js](https://nodejs.org/): 前端项目的运行环境。
- [npm](https://npmjs.com/): Node.js的包管理器，用于安装前端项目的依赖。
- [Maven](https://maven.apache.org/): Java项目管理和构建工具。

### 前端

在`frontend`目录下:

1. 安装依赖:
   ```sh
   npm install
   ```
2. 启动开发服务器:
   ```sh
   npm run dev
   ```

### 后端

在`Backend`目录下:

1. 通过Maven构建项目:
   ```sh
   mvn clean install
   ```
2. 运行Spring Boot应用:
   ```sh
   mvn spring-boot:run
   ```

