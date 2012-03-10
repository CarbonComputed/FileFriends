package Main;

import java.util.Arrays;

public class Utilities {
	public static byte[] mergeArrays(byte[]... data){
		int length=0;
		for(byte[] b:data){
			length+=b.length;
		}
		byte[] ret = new byte[length];
		int ctr=0;
		for(byte[] b:data){
			for(int x=0;x<b.length;x++){
				ret[ctr+x]=b[x];
			}
			ctr+=b.length;
		}
		return ret;
		
	}
	
	public static byte[] removeTrailingNulls(byte[] data){
		
		int ctr=0;
		for(int x=data.length-1;x>=0;x--){
			if(data[x]!='\u0000'){
				break;
			}
			ctr++;
		}
		return Arrays.copyOf(data, data.length-ctr);
		
		
	}
	public static void main(String args[]){
		byte[] buf = new byte[65000];
		buf[0]='h';
		buf[1]='e';
		buf[2]='l';
		buf[3]='l';
		buf[4]='o';
		System.out.println(new String(buf));
		System.out.println(buf.length);
		System.out.println(Utilities.removeTrailingNulls(buf).length);
	}
}
