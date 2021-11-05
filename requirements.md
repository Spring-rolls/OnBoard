# Software Requirements

## Vision

Our vision is to create a community for board games players, with a verified hosting mechanism, as well as for businesses willing to host such events.

### Problem domain

We want to help board games enjoyers to find new people and new places that they can comfortably visit without worrying about verification, we will make it easier for businesses to organize hosting events like this.

### Importance of this project

Along expanding the retro gaming community activities, we are injecting social development into our society, participating in activities like this has a healthy impact on society.

## Scope (In/Out)

### IN - What will our project do:
* Users (individuals or Entities) can post events that they want to host and can react with joiners to organize events.
* joiners can rate the host after every event, the overall score will depend on the number of ratings 
* Entities can host events and can offer some offers during those events.
* visitors can rate the place and this will impact the overall score for that place.

### OUT - What will our project not do:
*  Our project won't be hosting online games. 
*  We won't offer sponsorships for suggested events. 
*  We won't make a live chat service. 
*  We won't deal with gambling or adding a betting system.

### MVP

Our project MVP will be posting events, that will be a starting point and we will be building over that single functionality. 

### Stretch Goals

1. We will try to expand our services to make a wide variety of events like tournaments.
2. We will try to make themes for the website. 
3. We will try to add a support section, that users can use to contact us if they had a complaint or an inquiry.

## Functional Requirements

### functionalities: 
* individuals
1. Users can post events to host.
2. Users can join events from other hosts. 
3. users can manage their profiles.
* Entities 
1. Entities can host gaming sessions 
2. They can post offers during the sessions. 
3. They can manage their profiles.


### Data Flow 

![data](https://i.imgur.com/9Pcdupy.jpeg)

# Non-Functional Requirements

## Security

We will use the available authorizations and authentication methods to ensure that our user's data are safe and verified, we will be using `Spring Security`, which will manage the site data and will authenticate current users on login. 

## Usability

We will make the website user-friendly as much as we can, by making the navigation easy and designing a rigid structure where every component is smoothly blinding in without any useless additions, we will simulate the user experience through the website and try to plan his path so we can build a well accessbilty pages.
