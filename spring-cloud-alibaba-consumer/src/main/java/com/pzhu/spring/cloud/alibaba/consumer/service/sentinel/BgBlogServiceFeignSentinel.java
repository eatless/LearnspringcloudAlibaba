package com.pzhu.spring.cloud.alibaba.consumer.service.sentinel;

import com.pzhu.spring.cloud.alibaba.common.domain.BgBlog;
import com.pzhu.spring.cloud.alibaba.consumer.service.BgBlogServiceFeign;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * BgBlogServiceFeign的熔断器
 * @Author Jin haiyang
 * @Date 2020/3/15 18:05
 */
@Component
public class BgBlogServiceFeignSentinel implements BgBlogServiceFeign {
    @Override
    public List<BgBlog> list() {
        ArrayList list = new ArrayList<BgBlog>();
        BgBlog blog = new BgBlog();
        blog.setTitle("hello Sentinel");
        list.add(blog);
        return list;
    }
}
