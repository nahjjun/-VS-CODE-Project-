
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class baekjoon_11497 {
    static int T; // test 케이스
    static int N; // 각 테스트 케이스별 통나무 개수
    static int[] heigths; // 각 통나무의 높이

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        T = Integer.parseInt(br.readLine());

        for(int i=0; i<T; ++i){
            N = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            heigths = new int[N];
            for(int j=0; j<N; ++j){
                heigths[j] = Integer.parseInt(st.nextToken());
            }

            // heigths 오름차순 정렬
            Arrays.sort(heigths);

            // 통나무 배치할 배열
            int[] tmp = new int[N];
            int current=0;
            for(int j=0; j<N; ++j){
                if(j%2 == 0){ // 짝수인 경우 왼쪽에 추가
                    tmp[current] = heigths[j];
                }
                else{ // 홀수번째인 경우 오른쪽에 추가
                    tmp[N-current-1] = heigths[j];
                    current++;
                }
            }

            // 배치가 완료되면, 해당 배열에서 난이도를 측정한다.
            int max = Math.abs(tmp[N-1]-tmp[0]);
            for(int j=0; j<N-1; ++j){
                if(max < Math.abs(tmp[j+1]-tmp[j])){
                    max = Math.abs(tmp[j+1]-tmp[j]);
                }
            }
            System.out.println(max);
        }

        br.close();
    }

}
