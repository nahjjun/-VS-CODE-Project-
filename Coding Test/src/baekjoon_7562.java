// <문제>
// 체스판 위에 한 나이트가 놓여져 있다. 나이트가 한 번에 이동할 수 있는 칸은 아래 그림에
// 나와있다. 나이트가 이동하려고 하는 칸이 주어진다. 나이트는 몇 번 움직이면 이 칸으로 
// 이동할 수 있을까?

// <입력>
// 입력의 첫째 줄에는 테스트 케이스의 개수가 주어진다.
// 각 테스트 케이스는 세 줄로 이루어져 있다. 첫째 줄에는 체스판의 한 변의 길이 l(4 ≤ l ≤ 300)
// 이 주어진다. 체스판의 크기는 l × l이다. 체스판의 각 칸은 두 수의 쌍 {0, ..., l-1} × {0, 
// ..., l-1}로 나타낼 수 있다. 둘째 줄과 셋째 줄에는 나이트가 현재 있는 칸, 나이트가 이동하려고
// 하는 칸이 주어진다.

// <출력>
// 각 테스트 케이스마다 나이트가 최소 몇 번만에 이동할 수 있는지 출력한다

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class baekjoon_7562{
    static int n; // 테스트 케이스의 개수
    static int length; // 체스판 한 변의 길이
    static int first[];
    static int last[];

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        n = Integer.parseInt(br.readLine());
        first = new int[2];
        last = new int[2];

        for(int i=0; i<n; ++i){
            length = Integer.parseInt(br.readLine());

            st = new StringTokenizer(br.readLine());
            first[0] = Integer.parseInt(st.nextToken());
            first[1] = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            last[0] = Integer.parseInt(st.nextToken());
            last[1] = Integer.parseInt(st.nextToken());

            System.out.println(getSolution());
        }
    }

    public static class Node{
        int x, y;
        int count;
        public Node(int x, int y, int count){
            this.x = x;
            this.y = y;
            this.count = count;
        }

    }

    // 나이트가 최소 몇 번만에 이동할 수 있는지 출력하는 함수
    // 첫번째 생각한 방법 : dfs
    public static int getSolution(){
        int min = Integer.MAX_VALUE; // 최솟값

        Queue<Node> queue = new ArrayDeque<Node>();
        // 나이트가 갈 길을 만들어둔 배열
        int dx[] = {1, 2, 2, 1, -1, -2, -2, -1};
        int dy[] = {-2, -1, 1, 2, 2, 1, -1, -2};

        // 방문 여부
        boolean visited[][] = new boolean[length][length];

        Node n;
        n = new Node(first[0], first[1], 0);
        queue.add(n);
        visited[n.y][n.x] = true;

        while(!queue.isEmpty()){
            n = queue.poll();
            // 만약 해당 point가 last와 같다면 min 비교하고 continue;
            if(n.x == last[0] && n.y == last[1]){
                if(min > n.count){
                    min = n.count;
                }
                continue;
            }

            // 해당 Node가 last와 같지 않으면, 나이트가 갈 수 있는 모든 길을 전부 가본다
            Node newNode;
            for(int i=0; i<8; ++i){
                int nextX = n.x+dx[i];
                int nextY = n.y+dy[i];
                // 다음에 갈 곳이 범위 밖인지 확인
                if(nextX < 0 || nextX >=length || nextY < 0 || nextY>=length) continue;

                if(!visited[nextY][nextX]){ // 아직 방문하지 않은 곳이면 stack에 node 만들어서 push
                    newNode = new Node(nextX, nextY, n.count+1);
                    visited[nextY][nextX] = true;
                    queue.add(newNode);
                }
            }
        }
        return min;
    }

}




