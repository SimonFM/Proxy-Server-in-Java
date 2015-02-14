import java.util.*;
public class BlackList
{
	private List<String> blackList;
	BlackList()
	{
		blackList = new ArrayList<String>();
	}
	/***
	 * A function to check whether a host name is black listed
	 * @param s -  contains the host to see if it is black listed
	 */
	boolean isBlackListed(String hostName)
	{
		return blackList.contains(hostName);
	}
	/***
	 * A function to add a host to a blacklist
	 * @param s -  contains the host to black list
	 */
	void addToBlackListThis(String hostName)
	{
		if(!blackList.contains(hostName)) blackList.add(hostName);
		else System.out.println(hostName + " Aleady in the black-list!");
	}
	/***
	 * A function to remove a host from the black list
	 * @param s -  contains the host to remove from the black list
	 */
	void removeFromBlackList(String hostName)
	{
		if(!blackList.isEmpty()){
			if(blackList.contains(hostName)){
				blackList.remove(hostName);
				System.out.println(hostName +" was black-listed!");
			}
			else System.out.println(hostName + " is not black-listed!");
		}
		else System.out.println(hostName+" could not be removed, empty black-list!");
	}
}