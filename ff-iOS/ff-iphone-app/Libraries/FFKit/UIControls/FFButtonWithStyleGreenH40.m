//
//  FFButtonWithStyleGreenH40.m
//  FFiPhoneApp
//
//  Created by lee on 8/13/13.
//  Copyright (c) 2013 Feeding Forward. All rights reserved.
//

#import "FFButtonWithStyleGreenH40.h"

static NSString * const kButtonImageNormalState = @"button_green_40x40_normal.png";
static NSString * const kButtonImageHighlightedState = @"button_green_40x40_highlighted";
static NSString * const kButtonImageDisabledState = @"button_green_40x40_disabled.png";

@implementation FFButtonWithStyleGreenH40

- (id)initWithCoder:(NSCoder *)aDecoder
{
    self = [super initWithCoder:aDecoder];
    if (self)
    {
        // Initialization code
        
        UIEdgeInsets insets = UIEdgeInsetsMake(10, 10, 10, 10);
        [self setBackgroundColor:[UIColor clearColor]];
        // Normal state
        [self setBackgroundImage:[[UIImage imageNamed:kButtonImageNormalState] resizableImageWithCapInsets:insets] forState:UIControlStateNormal];
        [self setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
        // Highlighted state
        [self setBackgroundImage:[[UIImage imageNamed:kButtonImageHighlightedState] resizableImageWithCapInsets:insets] forState:UIControlStateHighlighted];
        [self setTitleColor:[UIColor whiteColor] forState:UIControlStateHighlighted];
        // Disabled state
        [self setBackgroundImage:[[UIImage imageNamed:kButtonImageDisabledState] resizableImageWithCapInsets:insets] forState:UIControlStateDisabled];
        [self setTitleColor:[UIColor lightGrayColor] forState:UIControlStateDisabled];
    }
    return self;
}

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
    }
    return self;
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect
{
    // Drawing code
}
*/

@end
