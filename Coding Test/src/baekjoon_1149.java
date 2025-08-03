// RGB거리에는 집이 N개 있다. 거리는 선분으로 나타낼 수 있고, 1번 집부터 N번 
//집이 순서대로 있다.

// 집은 빨강, 초록, 파랑 중 하나의 색으로 칠해야 한다. 각각의 집을 빨강, 초록,
// 파랑으로 칠하는 비용이 주어졌을 때, 아래 규칙을 만족하면서 모든 집을 칠하는 
// 비용의 최솟값을 구해보자.

// 1번 집의 색은 2번 집의 색과 같지 않아야 한다.
// N번 집의 색은 N-1번 집의 색과 같지 않아야 한다.
// i(2 ≤ i ≤ N-1)번 집의 색은 i-1번, i+1번 집의 색과 같지 않아야 한다.


// <입력>
// 첫째 줄에 집의 수 N(2 ≤ N ≤ 1,000)이 주어진다. 둘째 줄부터 N개의 줄에는 각 
// 집을 빨강, 초록, 파랑으로 칠하는 비용이 1번 집부터 한 줄에 하나씩 주어진다.
// 집을 칠하는 비용은 1,000보다 작거나 같은 자연수이다.

// <출력>
// 첫째 줄에 모든 집을 칠하는 비용의 최솟값을 출력한다

// bfs로 하면 2^n 의 시간복잡도가 나온다. 따라서, dp로 풀어야 하는 문제이다.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class baekjoon_1149 {
    static int N;
    static int[][] RGB; // 각 RGB별 가격


    public static void main(String[] args) throws IOException{
        // n 입력받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        RGB = new int[N][3]; // list 메모리 할당
        for(int i=0; i<N; ++i){
            st = new StringTokenizer(br.readLine());
            RGB[i][0] = Integer.parseInt(st.nextToken());
            RGB[i][1] = Integer.parseInt(st.nextToken());
            RGB[i][2] = Integer.parseInt(st.nextToken());
        }

        int result = dp();
        
        System.out.println(result);
    }

    // 최종 결과를 반환해주는 dp 함수
    public static int dp(){
        int[][] list = new int[3][N]; // 계산 결과를 저장할 list
        
        // list 처음 열 값 초기화
        list[0][0] = RGB[0][0];
        list[1][0] = RGB[0][1];
        list[2][0] = RGB[0][2];

        // 이전 열의 RGB 여부 => R:0, G:1, B:2
        int prev[] = new int[3];
        prev[0] = 0;
        prev[1] = 1;
        prev[2] = 2;

        // 1열 ~ N-1열까지 이전값 기준으로 값 갱신
        for(int col=1; col<N; col++){
            for(int i=0; i<3; i++){ // 각 좌표[i][col] 검사
                
                // 전 열에서 사용하지 않은 값들 중 최솟값 가져오기
                int min = Integer.MAX_VALUE;
                for(int j=0; j<3; j++){
                    if(j != prev[i] && min > RGB[col][j]) {
                        min = RGB[col][j];
                        // prev 값 최신화
                        prev[i] = j;
                    }
                }

                // 최솟값 가져오면, 해당 값과 이전의 값을 더해서 list에 최신화
                list[i][col] = list[i][col-1]+min;
            }
        }
        return Math.min(list[0][N-1], Math.min(list[1][N-1], list[2][N-1]));

    }

}
