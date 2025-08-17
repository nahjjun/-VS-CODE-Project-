import java.util.*;

public class PCCP_동영상재생기{   
    static int video_len; // 초로 표현한 영상 길이
    static int pos; // 초로 표현된 기능이 수행되기 직전의 재생 위치
    static int op_start, op_end; // 초로 표현된 오프닝 시작, 종료 시간    
    
    public String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {
        // 초기값 설정
        this.video_len = mTos(video_len);
        this.pos = mTos(pos);
        this.op_start = mTos(op_start);
        this.op_end = mTos(op_end);
        
        for(String command : commands){
            // 현재 위치가 오프닝 구간인지 확인한다.
            if(this.pos >= this.op_start && this.pos <= this.op_end){
                this.pos = this.op_end;
            }
            if(command.equals("next")){
                next();
            } else if(command.equals("prev")){
                prev();
            }
            // 끝과 마지막에 오프닝 구간인지 확인해야함
            if(this.pos >= this.op_start && this.pos <= this.op_end){
                this.pos = this.op_end;
            }
        }
        
        String result = sTom(this.pos);
        return result;
    }
    
    // 10초 이전으로 이동하는 함수. (pos값 수정)
    public static void prev(){
        pos -= 10;
        if(pos < 0) pos = 0;
    }
    
    // 10초 이후로 이동하는 함수
    public static void next(){
        pos += 10;
        if(pos > video_len) pos = video_len;
    }
    
    
    // 분 -> 초로 변환하는 함수
    public static int mTos(String time){
        String[] list = time.split(":");
        int minute = Integer.parseInt(list[0]);
        int second = Integer.parseInt(list[1]);
        
        return minute*60 + second;
    }
    
    // 초 -> 분으로 변환하는 함수
    public static String sTom(int time){
        String minute = Integer.toString(time/60);
        String second = Integer.toString(time%60);
        
        // 한자리인 경우, 앞에 0을 붙여줌
        if((time/60)/10 == 0) minute = "0" + minute;
        if((time%60)/10 == 0) second = "0" + second;
        
        return minute + ":" + second;
    }
    
}