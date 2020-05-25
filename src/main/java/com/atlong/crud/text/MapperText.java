package com.atlong.crud.text;

import com.atlong.crud.bean.Employee;
import com.atlong.crud.dao.DepartmentMapper;
import com.atlong.crud.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;
import java.util.UUID;

/**
 * @author : chen
 * @project:SSM-master
 * @date : 2020-05-23 16:40
 * @description:测试dao层的工作
 * 推荐Spring的项目就可以使用 spring的单元测试，就可以自动主人我们需要的组件
 * 1.先导入spring-test依赖
 * 2.@ContextConfiguration指定spring配置文件的位置
 * 3.直接autowired要使用的组件
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})               //上下文配置
public class MapperText {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;
    
    @Autowired
    SqlSession sqlSession;

    /**
     * 测试DepartmentMapper
     */
    @Test
    public void testMapper(){
        System.out.println(departmentMapper);
        nameText text = new nameText();
        System.out.println();

        //插入部门
        /*departmentMapper.insertSelective(new Department(null,"开发部"));
        departmentMapper.insertSelective(new Department(null,"开发部"));
        departmentMapper.insertSelective(new Department(null,"测试部"));*/

        //生成员工数据
        //employeeMapper.insertSelective(new Employee(null,"jerry","M","jerry@163.com",1));

        //批量插入多个员工 可以使用sqlsession来执行批量操作
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        for (int i = 0; i <1000 ; i++) {
            String uid = UUID.randomUUID().toString().substring(0, 8) + i;
            mapper.insertSelective(new Employee(null,build(),"M",getEmail(5,11),1));
        }
        System.out.println("批量完成");
    }



    //随机生成姓名
    private static String xings = "赵 钱 孙 李 周 吴 郑 王 冯 陈 褚 卫 " + "蒋 沈 韩 杨 朱 秦 尤 许 何 吕 施 张 " + "孔 曹 严 华 金 魏 陶 姜 戚 谢 邹 喻 "
            + "柏 水 窦 章 云 苏 潘 葛 奚 范 彭 郎 " + "万俟 司马 上官 欧阳 夏侯 诸葛 闻人 东方 赫连 皇甫 尉迟 公羊";
    private static String mings = "碧凡、夏菡、曼香、若烟、半梦、雅绿、冰蓝、灵槐、平安、书翠、翠风、香巧、代云、" + "友巧、听寒、梦柏、醉易、访旋、亦玉、凌萱、访卉、怀亦、笑蓝、春翠、靖柏、书雪、"
            + "乐枫、念薇、靖雁、寻春、恨山、从寒、忆香、觅波、静曼、凡旋、新波、代真、新蕾、" + "雁玉、冷卉、紫山、千琴、恨天、傲芙、盼山、怀蝶、冰兰、问旋、从南、白易、问筠、"
            + "如霜、半芹、寒雁、怜云、寻文、谷雪、乐萱、涵菡、海莲、傲蕾、青槐、冬儿、易梦、" + "惜雪、宛海、之柔、夏青";
    private static Random r = new Random();

    /**
     * 使用指定的姓氏，用xings里面的字，随机生成姓名
     * @param xing		指定姓氏
     * @param length	指定名字总长度
     * @return
     */
    public static String build(String xing, int length) {
        // 定义姓名
        String xingming = xing;

        //创建随机对象
        Random r = new Random();

        while (xingming.length() < length) {
            // 从名字符串中随机取出一个字符的编号
            int index = r.nextInt(mings.length());
            // 从名字字符串中取一个字
            String s = mings.substring(index, index + 1);
            // 如果s是顿号，就重新再取一次（使用常量比变量，这是推荐的方式）
            if ("、".equals(s)) {
                continue;
            } else {
                //将名 加到 姓名中取
                xingming += s;
            }
        }
        return xingming;
    }
    /**
     * 使用xings里面姓氏，用xings里面的字，随机生成姓名
     * @param length 指定名字总长度
     * @return
     */
    public static String build(int length) {
        // 判断姓名的长度必须大于 2
        if (length < 2) {
            System.out.println("姓名不能少于2个字符");
            return null;
        }
        /**
         * 因为有些姓氏不能拆分（如：复姓），因此选姓氏不能像选名字那样一个一个字选
         * 因此要将xings转成数组
         */
        // 先随机选姓氏
        Random r = new Random();
        String[] xingArr = xings.split(" ");
        int index = r.nextInt(xingArr.length);
        String xing = xingArr[index];

        // 有了姓氏，再调用上面的选名字的方法，就OK了
        return build(xing, length);
    }

    /**
     * 随机生成2~3个字的名字
     * @return
     */
    public static String build() {
        int length = r.nextInt(2) + 2;
        return build(length);
    }




    //随机生成邮箱
    private static final String[] email_suffix = "@gmail.com,@yahoo.com,@msn.com,@hotmail.com,@aol.com,@ask.com,@live.com,@qq.com,@0355.net,@163.com,@163.net,@263.net,@3721.net,@yeah.net,@googlemail.com,@126.com,@sina.com,@sohu.com,@yahoo.com.cn".split(",");
    public static String base = "abcdefghijklmnopqrstuvwxyz0123456789";

    public static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

    /**
     * 返回Email
     *
     * @param lMin 最小长度
     * @param lMax 最大长度
     * @return
     */
    public static String getEmail(int lMin, int lMax) {
        int length = getNum(lMin, lMax);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = (int) (Math.random() * base.length());
            sb.append(base.charAt(number));
        }
        sb.append(email_suffix[(int) (Math.random() * email_suffix.length)]);
        return sb.toString();
    }

    //  代码源于网络 由kingYiFan整理  create2019/05/24
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            String email = getEmail(5, i);
            System.out.println(email);
        }
    }
}
