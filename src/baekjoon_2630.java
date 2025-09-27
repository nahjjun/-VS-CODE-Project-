import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class baekjoon_2630 {
    static int N;
    static int[][] list;
    static int white, blue;

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

        white = 0;
        blue = 0;

        solution(N, 0, 0);

        System.out.println(white);
        System.out.println(blue);

        br.close();   
    }


    // 시작 좌표
    public static void solution(int n, int row, int col){
        // 1. n이 1이면 해당 좌표의 색깔 판별해서 증가시킴
        if(n==1){
            if(list[row][col] == 0) white++;
            else blue++;
            return;
        }

        // 2. 현재 상태가 안잘라도 되는 상태인지 확인한다.
        boolean tmp = true;
        int first = list[row][col];
        for(int i=row; i<row+n; ++i){
            for(int j=col; j<col+n; ++j){
                if(first != list[i][j]){
                    tmp=false;
                    break;
                }
            }
        }

        if(tmp){
            switch(first){
                case 0: white++; break;
                case 1: blue++; break;
            }
            return;
        }

        // 현재 상태가 잘라야되는 상태인 경우 
        // 1. 시작좌표 기준으로 n개의 길이만큼 정사각형이 있음.
        // 해당 정사각형 기준으로 4개로 나눈다.
        int[] sRow = new int[4];
        int[] sCol = new int[4];

        // 4개의 시작좌표 설정
        sRow[0] = row;
        sCol[0] = col;
        
        sRow[1] = row + n/2;
        sCol[1] = col;

        sRow[2] = row;
        sCol[2] = col + n/2;

        sRow[3] = row + n/2;
        sCol[3] = col + n/2;

        // 4개의 시작 좌표에서 재귀
        for(int i=0; i<4; ++i){
            solution(n/2, sRow[i], sCol[i]);
        }
    }
}
