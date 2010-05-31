//
//  DataStatusAppDelegate.h
//  DataStatus
//
//  Created by Luca Spiller on 31/05/2010.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import <Cocoa/Cocoa.h>

@interface DataStatusAppDelegate : NSObject <NSApplicationDelegate> {
    NSWindow *window;
	IBOutlet NSMenu *statusMenu;
    NSStatusItem * statusItem;
	NSTimer *timer;
}

@property (assign) IBOutlet NSWindow *window;

@end
