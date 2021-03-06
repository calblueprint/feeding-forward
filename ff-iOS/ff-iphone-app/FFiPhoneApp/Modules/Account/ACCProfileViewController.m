//
//  ACCProfileViewController.m
//  FFiPhoneApp
//
//  Created by lee on 8/8/13.
//  Copyright (c) 2013 Feeding Forward. All rights reserved.
//

#import "ACCProfileViewController.h"

#import "AccountModuleController.h"

#import "FFKit.h"

@interface ACCProfileViewController ()

@property (strong, nonatomic) FFDataUser *user;
@property (strong, nonatomic) IBOutlet UILabel *leftLabelName;
@property (strong, nonatomic) IBOutlet UILabel *leftLabelEmail;
@property (strong, nonatomic) IBOutlet UILabel *leftLabelPhone;
@property (strong, nonatomic) IBOutlet UILabel *leftLabelOrganization;

@property (strong, nonatomic) IBOutlet UILabel *labelFullName;
@property (strong, nonatomic) IBOutlet UILabel *labelEmail;
@property (strong, nonatomic) IBOutlet UILabel *labelMobilePhoneNumber;
@property (strong, nonatomic) IBOutlet UILabel *labelOrganization;
@property (strong, nonatomic) IBOutlet UISwitch *switchSettingShareDonationPopup;
@property (strong, nonatomic) IBOutlet UIBarButtonItem *menuButton;

- (IBAction)switchShareDonationPopupSetting_onValueChanged:(id)sender;
- (IBAction)buttonLogout_onTouchUpInside:(id)sender;

@end

@implementation ACCProfileViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    
    //Font used for labels
    UIFont *labelFont = [UIFont fontWithName:@"ProximaNovaA-Regular" size:16.0];
    UIFont *leftLabelFont = [UIFont fontWithName:@"ProximaNovaA-Regular" size:13.0];
    [self.leftLabelName setFont:leftLabelFont];
    [self.leftLabelEmail setFont:leftLabelFont];
    [self.leftLabelPhone setFont:leftLabelFont];
    [self.leftLabelOrganization setFont:leftLabelFont];
    
    [self.switchSettingShareDonationPopup setOn:[[[NSUserDefaults standardUserDefaults] valueForKey:kUserDefaultsShareDonationPopupKey] boolValue]];
    
    [self setUser:self.moduleController.user];

    [self.labelFullName setText:self.user.fullName];
    self.labelFullName.font = [UIFont fontWithName:@"ProximaNovaA-Regular" size:20];
    
    [self.labelEmail setText:self.user.email];
    [self.labelEmail setFont:labelFont];
    
    [self.labelMobilePhoneNumber setText:self.user.mobilePhoneNumber];
    [self.labelMobilePhoneNumber setFont:labelFont];
    
    [self.labelOrganization setText:self.user.organization];
    [self.labelOrganization setFont:labelFont];
    
    
    [self.menuButton setImage:[UIImage imageNamed:@"menu.png"]];
    [self.menuButton setTintColor:[UIColor colorWithRed:46/255.0 green:46/255.0 blue:46/255.0 alpha:0.65]];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)switchShareDonationPopupSetting_onValueChanged:(id)sender
{
    [[NSUserDefaults standardUserDefaults] setValue:[NSNumber numberWithBool:self.switchSettingShareDonationPopup.on]
                                             forKey:kUserDefaultsShareDonationPopupKey];
    [[NSUserDefaults standardUserDefaults] synchronize];
}

- (IBAction)buttonLogout_onTouchUpInside:(id)sender
{
    [self.moduleController logoutUser];
}

- (IBAction)menuButtonPressed:(id)sender
{
    [self.moduleController.mmDrawerController openDrawerSide:MMDrawerSideLeft animated:YES completion:nil];
    
}
@end
