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
    // dp에서 쓸 배열
    static int[][] list;

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

        list = new int[3][N]; // 계산 결과를 저장할 list
        
        // list 마지막값 설정
        list[0][N-1] = RGB[N-1][0];
        list[1][N-1] = RGB[N-1][1];
        list[2][N-1] = RGB[N-1][2];

        int result1 = dp(0, 0);
        int result2 = dp(1, 0);
        int result3 = dp(2, 0);
        
        System.out.println(Math.min(result1, Math.min(result2, result3)));
    }

    // 최종 결과를 반환해주는 dp 함수 (top-down 방식)
    public static int dp(int row, int col){
        //  현재 있는 좌표가 이미 채워져있는 경우(해당 값은 이미 최솟값이므로) 
        // OR 현재 있는 열이 N인 경우, 해당 좌표값 return하기
        if(col == N || list[row][col] > 0){
            return list[row][col];
        }
        
        // 현재 좌표가 채워지지 않은 경우, 두 경우의 수를 비교해서 계산한다
        int plus = Math.min(dp((row+1)%3, col+1), dp((row+2)%3, col+1));
        
        list[row][col] = RGB[col][row] + plus;
        return list[row][col];
    }

}
