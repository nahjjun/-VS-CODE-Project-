
// <문제>
// 한수는 크기가 2N × 2N인 2차원 배열을 Z모양으로 탐색하려고 한다. 예를 들어, 
// 2×2배열을 왼쪽 위칸, 오른쪽 위칸, 왼쪽 아래칸, 오른쪽 아래칸 순서대로 방문하면 Z모양이다.
// N > 1인 경우, 배열을 크기가 2N-1 × 2N-1로 4등분 한 후에 재귀적으로 순서대로 방문한다.
// 다음 예는 22 × 22 크기의 배열을 방문한 순서이다.
// N이 주어졌을 때, r행 c열을 몇 번째로 방문하는지 출력하는 프로그램을 작성하시오.

// <입력>
// 첫째 줄에 정수 N, r, c가 주어진다.

// <출력>
// r행 c열을 몇 번째로 방문했는지 출력한다.

// <제한>
// 1 ≤ N ≤ 15
// 0 ≤ r, c < 2N

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class baekjoon_1074 {
    static int N, r, c;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        // 해당 좌표가 현재 가장 큰 박스를 4등분했을 때 어느 박스에 들어있는지 확인
        
        int length = (int)Math.pow(2, N);
        int result = split(0, 0, 0, length);

        System.out.println(result);
        br.close();
    }


    // 기준점의 값, 기준점의 좌표, 해당 박스의 한 변의 길이
    public static int split(int standPrice, int standX, int standY, int length){
        // 한 변의 길이가 1이 되는 순간, 해당 순간의 standPrice를 return한다.
        if(length == 1){
            return standPrice;
        }
        int result = 0;

        int where = 0; // 0, 1, 2, 3 중 어디 구역인지 저장하는 변수

        // 한 변의 길이가 1이 아니라면, r행 c열의 좌표가 해당 상자에서 어느 위치에 속해 있는지 확인한다.

        // 해당 상자에서 상대적인 행렬 인덱스
        int row = r-standY;
        int col = c-standX;
        int half = length/2;

        // 1. 0번 상자에 있는가?
        if(row>=0 && row<half && col >=0 && col<half){
            where = 0;
            // 0번 상자는 standX와 standY가 그대로이다.
        }
        // 2. 1번 상자에 있는가?
        if(row>=0 && row<half && col >=half && col<length){
            where = 1;
            // 1번 상자는 standX+half
            standX += half;
        }
        // 3. 2번 상자에 있는가?
        if(row>=half && row<length && col >=0 && col<half){
            where = 2;
            standY += half;
        }
        // 4. 3번 상자에 있는가?
        if(row>=half && row<length && col >=half && col<length){
            where = 3;
            standX += half;
            standY += half;
        }

        standPrice += (half * half * where);


        result = split(standPrice, standX, standY, half);
        return result;
    }

}
