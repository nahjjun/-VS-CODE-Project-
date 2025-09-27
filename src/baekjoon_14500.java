
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;



public class baekjoon_14500 {
    static int N, M;
    static int[][] list;
    static int max;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        list = new int[N][M];

        for(int i=0; i<N; ++i){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<M; ++j){
                list[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        max = Integer.MIN_VALUE;

        for(int y=0; y<N; ++y){
            for(int x=0; x<M; ++x){
                int w1 = Integer.MIN_VALUE;
                for(int v=0; v<=1; ++v){
                    w1 = Math.max(w1, getWeight1(x, y, v));
                }

                int w2 = getWeight2(x, y);
                int w3 = Integer.MIN_VALUE;
                for(int v=0; v<=7; ++v){
                    w3 = Math.max(w3, getWeight3(x, y, v));
                }
                int w4 = Integer.MIN_VALUE;
                for(int v=0; v<=3; ++v){
                    w4 = Math.max(w4, getWeight4(x, y, v));
                }
                int w5 = Integer.MIN_VALUE;
                for(int v=0; v<=3; ++v){
                    w5 = Math.max(w5, getWeight5(x, y, v));
                }

                int tmp = Math.max(w1, Math.max(Math.max(w2, w3), Math.max(w4, w5)));
                max = Math.max(tmp, max);
            }
        }

        System.out.println(max);
        br.close();
    }


    // x, y좌표 기준으로 수들의 합을 구하는 함수 (1번 블록, 파란색)
    public static int getWeight1(int x, int y, int version){        
        int result = 0;
        // 가로 모양인 경우
        if(version==0){
            if(x+3 >= M) return -1;
            for(int i=0; i<4; ++i){
                result += list[y][x+i];
            }
        } 
        // 세로 모양인 경우
        else {
            if(y+3 >= N) return -1;
            for(int i=0; i<4; ++i){
                result += list[y+i][x];
            }
        }
        return result;
    }

    // 정사각형 블록
    public static int getWeight2(int x, int y){
        if(x+1 >= M || y+1 >= N){
            return -1;
        }
        int result = 0;
        result += list[y][x];
        result += list[y][x+1];
        result += list[y+1][x];
        result += list[y+1][x+1];
        return result;
    }


    // 주황색 블록
    // 8가지 경우의 수 존재
    public static int getWeight3(int x, int y, int version){
        int dx[]=null, dy[]=null;
        switch (version) {
            case 0:
                dx = new int[]{0, 0, 0, 1};
                dy = new int[]{0, 1, 2, 2};
                break;
            case 1:
                dx = new int[]{0, 0, 1, 2};
                dy = new int[]{0, 1, 0, 0};
                break;
            case 2:
                dx = new int[]{0, 1, 1, 1};
                dy = new int[]{0, 0, 1, 2};
                break;
            case 3:
                dx = new int[]{0, 1, 2, 2};
                dy = new int[]{0, 0, 0, -1};
                break;
            case 4:
                dx = new int[]{0, 0, 0, -1};
                dy = new int[]{0, 1, 2, 2};
                break;
            case 5:
                dx = new int[]{0, 1, 2, 2};
                dy = new int[]{0, 0, 0, 1};
                break;
            case 6:
                dx = new int[]{0, 1, 0, 0};
                dy = new int[]{0, 0, 1, 2};
                break;
            case 7:
                dx = new int[]{0, 0, 1, 2};
                dy = new int[]{0, 1, 1, 1};
                break;
        }
        int result = 0;
        for(int i=0; i<4; ++i){
            if(x + dx[i] < 0 || x + dx[i] >= M || y + dy[i] < 0 || y + dy[i] >= N){
                return -1;
            }
            result += list[y+dy[i]][x+dx[i]];
        }
        return result;
    }


    // z 모양 블록 (4가지 존재)
    public static int getWeight4(int x, int y, int version){
        int dx[]=null, dy[]=null;
        switch (version) {
            case 0:
                dx = new int[]{0, 1, 1, 2};
                dy = new int[]{0, 0, -1, -1};
                break;
            case 1:
                dx = new int[]{0, 0, 1, 1};
                dy = new int[]{0, 1, 1, 2};
                break;
            case 2:
                dx = new int[]{0, 1, 1, 2};
                dy = new int[]{0, 0, 1, 1};
                break;
            case 3:
                dx = new int[]{0, 0, 1, 1};
                dy = new int[]{0, -1, -1, -2};
                break;
        }
        int result = 0;
        for(int i=0; i<4; ++i){
            if(x + dx[i] < 0 || x + dx[i] >= M || y + dy[i] < 0 || y + dy[i] >= N){
                return -1;
            }
            result += list[y+dy[i]][x+dx[i]];
        }
        return result;
    }

     // ㅏ 모양 블록 (4가지 존재)
    public static int getWeight5(int x, int y, int version){
        int dx[]=null, dy[]=null;
        switch (version) {
            case 0:
                dx = new int[]{0, 1, 1, 2};
                dy = new int[]{0, 0, -1, 0};
                break;
            case 1:
                dx = new int[]{0, 0, 0, 1};
                dy = new int[]{0, 1, 2, 1};
                break;
            case 2:
                dx = new int[]{0, 1, 1, 2};
                dy = new int[]{0, 0, 1, 0};
                break;
            case 3:
                dx = new int[]{0, 1, 1, 1};
                dy = new int[]{0, 0, -1, 1};
                break;
        }
        int result = 0;
        for(int i=0; i<4; ++i){
            if(x + dx[i] < 0 || x + dx[i] >= M || y + dy[i] < 0 || y + dy[i] >= N){
                return -1;
            }
            result += list[y+dy[i]][x+dx[i]];
        }
        return result;
    }

}
