# LearnspringcloudAlibaba
学习阿里巴巴项目

# 项目启动
下载一个nacos，然后启动nacos配置即。
nacos下载地址： https://github.com/alibaba/nacos

建表语句
```
CREATE TABLE `bg_blog` (
  `blog_id` bigint(20) NOT NULL COMMENT '主键',
  `category_id` bigint(20) NOT NULL COMMENT 'category_id',
  `title` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'title',
  `summary` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'summary',
  `status` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'status',
  `weight` bigint(20) DEFAULT NULL COMMENT 'weight',
  `support` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'support',
  `click` bigint(20) DEFAULT NULL COMMENT 'click',
  `header_img` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'header_img',
  `type` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'type',
  `content` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'content',
  `create_by` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'create_by',
  `update_by` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'update_by',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`blog_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
```

