import java.util.*;
import java.lang.*;

// 접근법은 맞았지만, 구현에서 실패한 문제였음

public class PCCP_아날로그시계 {
    static int startSec, endSec; // 시작, 종료 시간   
    static double hCurrent, mCurrent, sCurrent; // 시,분,초침 현재 각도
    static double hNext, mNext, sNext; // 다음 각도
    
    public int solution(int h1, int m1, int s1, int h2, int m2, int s2) {
        int result=0;
        
        // 시작, 종료 시간을 초로 변환
        startSec = h1 * 3600 + m1*60 + s1; 
        endSec = h2 * 3600 + m2*60 + s2; 
        
        // next 기준으로 계산할 것이므로, 포함되지 않는 시작시간인 00시, 12시는 미리 카운트 해놓는다,
        if(startSec == 0 || startSec == 12 * 3600){
            result++;
        }
            
        
        
        // startSec부터 endSec까지 초별로 이전/이후 초침 각도 & 이전/이후 분,시침 각도 비교
        while(startSec < endSec){
            // 시침 -> 1시간에 30도 이동 -> 1초에 30/3600 = 1/120도 이동
            // 분침 -> 1초에 1/10도 이동
            // 초침 -> 1초에 6도 이동
            
            // 현재 침들의 각도 계산
            hCurrent = (double)startSec/120;
            while(!(hCurrent>=0 && hCurrent<360)){
                hCurrent -= 360;
            }
            
            mCurrent = (double)startSec/10;
            while(!(mCurrent>=0 && mCurrent<360)){
                mCurrent -= 360;
            }
            
            sCurrent = (double)startSec*6;
            while(!(sCurrent>=0 && sCurrent<360)){
                sCurrent -= 360;
            }
            
            // 다음으로 이동할 각도가 360도에 딱 맞다면, 다음 각도를 360도로 설정해준다.
            // 360도로 설정해주지 않으면 0으로 설정되어서 카운팅에 포함되지 않는다
            
            // double형의 % 연산을 대체해주기 위해서 while문으로 0~360 범위가 되도록 360씩 빼준다.
            // BigDecimal 안쓰려고 이렇게 함
            hNext = ((double)startSec+1)/120;
            while(!(hNext>=0 && hNext<360)){
                hNext -= 360;
            }
            if(hNext == 0) hNext = 360;
            
            mNext = ((double)startSec+1)/10;  
            while(!(mNext>=0 && mNext<360)){
                mNext -= 360;
            }
            if(mNext == 0) mNext = 360;
            
            
            sNext = ((double)startSec+1)*6;
            while(!(sNext>=0 && sNext<360)){
                sNext -= 360;
            }
            if(sNext == 0) sNext = 360;
            
            
            // 초침-분침 & 초침-시침 비교
            if(sCurrent < mCurrent && sNext >= mNext) result++;
            if(sCurrent < hCurrent && sNext >= hNext) result++;
            
            // 시/분침과 초침이 동시에 겹첬을 때 중복 카운팅을 제외함
            if(sNext == hNext && hNext == mNext) result--;
            
            startSec++;
        }
        
        return result;
    }
}

