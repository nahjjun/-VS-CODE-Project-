import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;

public class baekjoon_11279 {
    static int n; // 입력받을 자연수 개수
    static PriorityQueue<Integer> queue;
    static int tmp;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        queue = new PriorityQueue<>(new Comparator<Integer>(){
            @Override
            public int compare(Integer a1, Integer a2){
                return a2-a1;
            }
        });

        for(int i=0; i<n; ++i){
            tmp = Integer.parseInt(br.readLine());
            if(tmp == 0){
                if(!queue.isEmpty()) 
                    System.out.println(queue.poll());
                else
                    System.out.println("0");
            } else {
                queue.add(tmp);
            }
        }
        

        br.close();
    }


}
