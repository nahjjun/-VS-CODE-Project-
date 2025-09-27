import java.io.*;
import java.util.*;

public class baekjoon_2002{
    static int N;
    static ArrayList<String> daguen = new ArrayList<>();
    static HashMap<String, Integer> yungsik = new HashMap<>();
    
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        N = Integer.parseInt(br.readLine());
        int i;
        // 대근과 영식의 입력을 받아들인다
        for(i=0; i<N; ++i){
            daguen.add(br.readLine());
            
        }
        for(i=0; i<N; ++i){
            yungsik.put(br.readLine(), i);
        }
        
        int result = getCarNum();
        System.out.println(result);
    }
    
    // 주어진 2개의 해시맵으로 추월했을 것으로 여겨지는 차를 구하는 함수
    public static int getCarNum(){
        // 제일 앞에 있는 차는 추월을 했을 가능성이 없는 것으로 간주한다.
        // 제일 앞에 있는 차를 A라고 하자.
        // 이때, 대근의 리스트를 순회하며, 영식Map.get(A)<영식Map.get(i)인 것들을
        // 영식Map에서 제외한다. (결과값은 해당 개수만큼 증가시킨다)
        // + boolean 배열최신화
        // 이후, boolean 배열이 false인 값에서 위 과정을 반복한다.
        
        int result = 0; // 결과
        boolean pass[] = new boolean[N];
        
        for(int i=0; i<N; ++i){
            if(pass[i]){ // pass배열의 값이 true라는 것은 이미 추월을 진행한 차라는 것
                continue;
            }
            String currentCar = daguen.get(i); // 검사할 기준 차를 얻어옴
            int currentPlace = yungsik.get(currentCar); // 검사할 기준 차의 위치 가져옴
            for(int j=i+1; j<N; ++j){ // 검사할 기준 차 위치 다음의 차들 중 pass값이 false인 차들만 검사한다.
                if(!pass[j]){
                    String nextCar = daguen.get(j);
                    // 만약 검사할 다음 차량이 현재 검사할 기준 차보다 앞에 있다면, result값 증가시켜주고
                    // pass true로 설정
                    if(currentPlace > yungsik.get(nextCar)){ 
                        result++;
                        pass[j] = true;
                    }
                }
            }
            
            pass[i] = true;
        }
        return result;
    }
}


