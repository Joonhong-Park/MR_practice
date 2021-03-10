import org.junit.*;

public class JunitTest {

    @Before
    public void before(){
        System.out.println("before");
    }

    @Test
    public void test(){
        System.out.println("test!");
    }
    @Ignore
    @Test
    public void test2(){
        System.out.println("test2!");
    }

    @Test
    public void test3(){
        System.out.println("test3!");
    }


    @After
    public void after(){
        System.out.println("after");
    }

}
