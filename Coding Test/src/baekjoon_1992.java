import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class baekjoon_1992 {
    static int N;
    static int[][] list;
    static StringBuilder sb;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        list = new int[N][N];

        for(int i=0; i<N; ++i){
            String tmp = br.readLine();
            char cList[] = tmp.toCharArray();
            for(int j=0; j<N; ++j){
                list[i][j] = cList[j]-'0';
            }
        }


        sb = new StringBuilder();

        if(!isAllSame(N, 0, 0)){
            sb.append("(");
            solution(N, 0, 0);
            sb.append(")");
        }else{
            sb.append(String.valueOf(list[0][0]));
        }
        
        System.out.println(sb.toString());
        br.close();   
    }


    public static void solution(int n, int row, int col){
        // 1. 현재 리스트의 내용이 전부 같은지 확인
        boolean allSame = isAllSame(n,row,col);

        // 현재 리스트의 내용이 전부 같다면, 첫번째 값을 sb에 추가하고 return;
        if(allSame){
            sb.append(list[row][col]);
            return;
        }

        // 현재 리스트의 내용이 전부 같지 않다면, 현재 리스트를 4등분한다.
        int sRow[] = new int[4];
        int sCol[] = new int[4];
        sRow[0] = row;
        sCol[0] = col;
        sRow[1] = row;
        sCol[1] = col+ n/2;
        sRow[2] = row+ n/2;
        sCol[2] = col;
        sRow[3] = row + n/2;
        sCol[3] = col + n/2;
        
        for(int i=0; i<4; ++i){
            // n/2의 값이 1이거나 해당 구간의 내용이 모두 같은 경우, 해당 자리의 숫자를 sb에 추가합니다.
            if(n/2==1 || isAllSame(n/2, sRow[i], sCol[i])){
                sb.append(list[sRow[i]][sCol[i]]);
            } else { // 만약 해당 구간의 내용이 전부 같지 않은 경우, 해당 구간으로 재귀적으로 들어간다.
                sb.append("(");
                solution(n/2, sRow[i], sCol[i]);
                sb.append(")");
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
