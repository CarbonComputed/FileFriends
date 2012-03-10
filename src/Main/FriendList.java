package Main;


import java.util.ArrayList;


public class FriendList {
	private ArrayList<Friend> friends;
	public FriendList(){
		friends= new ArrayList<Friend>();
	}
	
	
	public void add(Friend friend){
		if(!friends.contains(friend)){
			friends.add(friend);
		}
		
	}
	
	public Friend get(int x){
		if(x >=0 && x < friends.size() && !friends.isEmpty()){
			return friends.get(x);
		}
		return null;
	}
	
	public void remove(Friend friend){
		if(friends.contains(friend)){
			friends.remove(friend);
		}
		
	}
	
	public ArrayList<Friend> getFriends(){
		return friends;
	}
	
}
