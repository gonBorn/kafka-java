/*
    自动地从所有声明的字段中派生出equals()、hashCode()和toString()方法
    record类中的所有字段都是final的
    自动地生成一个公开的访问器方法。这个方法的名字就是字段的名字
 */
public record Student(String name, String studentId){}
