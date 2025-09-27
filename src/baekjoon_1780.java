import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class baekjoon_1780 {
    static int N; 
    static int[][] list;
    static int minus, zero, one; // -1, 0, 1의 개수

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        list = new int[N][N];

        for(int i=0; i<N; ++i){
            st = new StringTokenizer(br.readLine());

            for(int j=0; j<N; ++j){
                list[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 값 초기화
        minus = 0;
        zero=0;
        one=0;

        solution(N, 0, 0);

        System.out.println(minus);
        System.out.println(zero);
        System.out.println(one);
        br.close();
    }

    // 솔루션 함수
    public static void solution(int n, int row, int col){
        // n이 1이거나, 전부 같은지 확인
        // 그런 경우, 하나씩 증가시키고 종료
        if(n == 1 || isAllSame(n, row, col)){
            switch (list[row][col]) {
                case -1:
                    minus++;
                    break;
                case 0:
                    zero++;
                    break;
                case 1:
                    one++;
                    break;
            }
            return;
        }

        // 아닌 경우, 9개로 분할
        for(int i=0; i<3; ++i){
            for(int j=0; j<3; ++j){
                int currentR = row + i*(n/3);
                int currentC = col + j*(n/3);

                solution(n/3, currentR, currentC);
            }
        }


    }


    public static boolean isAllSame(int n, int row, int col){
        boolean allSame = true;
        int first = list[row][col];
        for(int i=row; i<row+n; ++i){
            for(int j=col; j<col+n; ++j){
                if(first!=list[i][j]){
                    allSame = false;
                    break;
                }
            }
        }
        return allSame;
    }
}
