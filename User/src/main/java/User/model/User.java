package User.model;

import com.dto.UserDto;
import com.dto.UserPreferencesDto;
import com.dto.UserRewardDto;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.model.Provider;
import com.model.VisitedLocation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * User Model
 */
public class User {
	private UUID userId;
	private String userName;
	private String phoneNumber;
	private String emailAddress;
	private Date latestLocationTimestamp;
	private List<VisitedLocation> visitedLocations = new ArrayList<>();
	private List<UserRewardDto> userRewardsDto = new ArrayList<>();
	private UserPreferences userPreferences = new UserPreferences();
	private List<Provider> tripDeals = new ArrayList<>();

	/**
	 * Default empty constructor
	 */
	public User() {
	}

	/**
	 * Default constructor
	 *
	 * @param userId User id
	 * @param userName User name
	 */
	public User(UUID userId, String userName) {
		this.userId = userId;
		this.userName = userName;

	}

	/**
	 * Full constructor
	 *
	 * @param userId User id
	 * @param userName User name
	 * @param phoneNumber User phone number
	 * @param emailAddress User email
	 */
	public User(UUID userId, String userName, String phoneNumber, String emailAddress, Date latestLocationTimestamp) {
		this.userId = userId;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.latestLocationTimestamp = latestLocationTimestamp;
	}

	public UUID getUserId() {
		return userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setLatestLocationTimestamp(Date latestLocationTimestamp) {
		this.latestLocationTimestamp = latestLocationTimestamp;
	}
	
	public Date getLatestLocationTimestamp() {
		return latestLocationTimestamp;
	}
	
	public void addToVisitedLocations(VisitedLocation visitedLocation) {
		visitedLocations.add(visitedLocation);
	}
	
	public List<VisitedLocation> getVisitedLocations() {
		return (List<VisitedLocation>)((ArrayList<VisitedLocation>)visitedLocations).clone();
	}

//	public List<VisitedLocation> getVisitedLocations() {
//		return visitedLocations;
//	}

	public void setVisitedLocations(List<VisitedLocation> visitedLocations) {
		this.visitedLocations = visitedLocations;
	}
	
	public void clearVisitedLocations() {
		visitedLocations.clear();
	}
	
	public void addUserReward(UserRewardDto userRewardDto) {
		if(userRewardsDto.stream().count() == 0) {
			userRewardsDto.add(userRewardDto);
		}
	}
	
	public List<UserRewardDto> getUserRewards() {
		return userRewardsDto;
	}
	
	public UserPreferences getUserPreferences() {
		return userPreferences;
	}
	
	public void setUserPreferences(UserPreferences userPreferences) {
		this.userPreferences = userPreferences;
	}

	public VisitedLocation getLastVisitedLocation() {
		if (visitedLocations.isEmpty()) {
			return null;
		}
		return visitedLocations.get(visitedLocations.size()-1);
	}
	
	public void setTripDeals(List<Provider> tripDeals) {
		this.tripDeals = tripDeals;
	}
	
	public List<Provider> getTripDeals() {
		return tripDeals;
	}

	public void clearRewards() {
		userRewardsDto.clear();
	}
}