public class Example{

    public void foo(String x, String a, String c, String y){
        if (x!=null)
            return;
        if(a.length()==0)
           return; 
        if(c == null){
            return; 
            
        }
        process(y);
    }

    public void process(String y){
        System.out.println(y);
    }
}