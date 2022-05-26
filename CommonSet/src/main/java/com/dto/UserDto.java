package com.dto;

import com.model.VisitedLocation;
import com.model.Provider;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * User Model
 */
public class UserDto {
	private UUID userId;
	private String userName;
	private String phoneNumber;
	private String emailAddress;
	private Date latestLocationTimestamp;
	private List<VisitedLocation> visitedLocations = new ArrayList<>();
	private List<UserRewardDto> userRewardsDto = new ArrayList<>();
	private UserPreferencesDto userPreferencesDto = new UserPreferencesDto();
	private List<Provider> tripDeals = new ArrayList<>();

	/**
	 * Default empty constructor
	 */
	public UserDto() {
	}

	/**
	 * Default constructor
	 *
	 * @param userId User id
	 * @param userName User name
	 */
	public UserDto(UUID userId, String userName) {
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
	public UserDto(UUID userId, String userName, String phoneNumber, String emailAddress) {
		this.userId = userId;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
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
	
	public UserPreferencesDto getUserPreferences() {
		return userPreferencesDto;
	}
	
	public void setUserPreferences(UserPreferencesDto userPreferencesDto) {
		this.userPreferencesDto = userPreferencesDto;
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