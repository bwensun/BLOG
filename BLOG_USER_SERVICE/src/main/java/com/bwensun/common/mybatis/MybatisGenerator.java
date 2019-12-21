package com.bwensun.common.mybatis;

import org.mybatis.generator.api.ShellRunner;

/**
 * @author 郑建雄
 * @date 2019/12/17
 */
public class MybatisGenerator {
    public static void main(String[] args) {
        String path = "D:\\ProgramFile\\idea_workspace\\DUBBO_TEST\\BLOG\\BLOG_USER_SERVICE\\src\\main\\resources\\generatorConfig.xml";
        args = new String[]{"-configfile", path, "-overwrite"};
        ShellRunner.main(args);
    }
}
