//
//  DataStatusAppDelegate.m
//  DataStatus
//
//  Created by Luca Spiller on 31/05/2010.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "DataStatusAppDelegate.h"
#import "JSON.h"

@implementation DataStatusAppDelegate

@synthesize window;

- (void)applicationDidFinishLaunching:(NSNotification *)aNotification
{
	// Insert code here to initialize your application 
}

-(void)awakeFromNib
{
	// setup menu item
	statusItem = [[[NSStatusBar systemStatusBar] statusItemWithLength:NSVariableStatusItemLength] retain];
	[statusItem setMenu:statusMenu];
	[statusItem setTitle:@"-"];
	[statusItem setHighlightMode:YES];
	
	// setup timer
	[timer invalidate];
	timer = [NSTimer scheduledTimerWithTimeInterval:3 target:self selector:@selector(updateStatus:) userInfo:NULL repeats:YES];
}

-(int)parseResponse:(NSString *)jsonResult
{
	@try
	{
		// Create SBJSON object to parse JSON
		SBJSON *parser = [[SBJSON alloc] init];
		
		// parse the JSON string into an object - assuming json_string is a NSString of JSON data
		NSDictionary *object = [parser objectWithString:jsonResult error:nil];
		return [[object objectForKey:@"dataConnectionType"] intValue];
	}
	
	@catch (NSException *exception)
	{
		NSLog(@"parseResponse: Caught %@: %@", [exception name], [exception reason]);
	}
	
	return 0;
}

- (int)getDataConnectionType
{
	@try
	{		
		// TODO detect router IP
		
		// build request
		NSString *urlString = [NSString stringWithFormat:@"http://192.168.43.1:17362/status"];
		NSMutableURLRequest *request = [[[NSMutableURLRequest alloc] init] autorelease];
		[request setURL:[NSURL URLWithString:urlString]];
		
		// get response
		NSHTTPURLResponse* urlResponse = nil;  
		NSError *error = [[NSError alloc] init]; 
		NSData *responseData = [NSURLConnection sendSynchronousRequest:request returningResponse:&urlResponse error:&error];  
		NSString *result = [[NSString alloc] initWithData:responseData encoding:NSUTF8StringEncoding];
		
		if ([urlResponse statusCode] >= 200 && [urlResponse statusCode] < 300) {
			// parse json response
			return [self parseResponse:result];
		}
	}
	
	@catch (NSException *exception)
	{
		NSLog(@"parseResponse: Caught %@: %@", [exception name], [exception reason]);
	}
	
	return 0;
}

- (void)updateStatus:(NSTimer *)myTimer
{
	int dataType = [self getDataConnectionType];
	
	NSString* statusString;
	
	switch(dataType)
	{
		// dataType is one of the TelephonyManager.NETWORK_TYPE_* values
		// See http://developer.android.com/reference/android/telephony/TelephonyManager.html
			
		case 8: // NETWORK_TYPE_HSDPA
		case 9: // NETWORK_TYPE_HSUPA
		case 10: // NETWORK_TYPE_HSPA
			statusString = @"H";
			break;
			
		case 3: // NETWORK_TYPE_UMTS
			statusString = @"3G";
			break;
			
		case 2: // NETWORK_TYPE_EDGE
			statusString = @"E";
			break;
			
		case 1: // NETWORK_TYPE_GPRS
			statusString = @"G";
			break;
			
		// unknown or no connection
		default:
			statusString = @"-";
			break;
	}
	
	[statusItem setTitle:statusString];
}
	
@end
