//
//  Dashboard.h
//  FFiPhoneApp
//
//  Created by lee on 8/10/13.
//  Copyright (c) 2013 Feeding Forward. All rights reserved.
//

#import <Foundation/Foundation.h>

@class FFDataUser, AuthenticationModuleController, CurrentDonationsModuleController, AccountModuleController, PostDonationModuleController, PastDonationsModuleController;

@interface Dashboard : NSObject

@property (strong, nonatomic) AuthenticationModuleController *authenticationModuleController;
@property (strong, nonatomic) CurrentDonationsModuleController *currentDonationsModuleController;
@property (strong, nonatomic) PastDonationsModuleController *pastDonationsModuleController;
@property (strong, nonatomic) AccountModuleController *accountModuleController;
@property (strong, nonatomic) PostDonationModuleController *postDonationModuleController;
@property (strong, nonatomic) UITabBarController *tabBarController;

+ (instancetype)sharedDashboard;
- (UITabBarController *)instantiateTabBarControllerWithUser:(FFDataUser *)user;
- (UITabBarController *)tabBarControllerWithUpdatedUser:(FFDataUser *)user;
- (UITabBarController *)tabBarControllerWithReloadedPostDonationView;

@end
