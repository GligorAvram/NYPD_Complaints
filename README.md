# NYPD Complaints

NYPD Complaints is a offense tracking program that uses information from the NYPD database available for download here:
https://data.cityofnewyork.us/Public-Safety/NYPD-Complaint-Data-Historic/qgea-i56i


The program tracks only 23 of the fields available from the main database:\
01	CMPLNT_NUM:	Randomly generated persistent ID for each complaint \
02	CMPLNT_FR_DT:	Exact date of occurrence for the reported event (or starting date of occurrence, if CMPLNT_TO_DT exists)\
03	CMPLNT_FR_TM:	Exact time of occurrence for the reported event (or starting time of occurrence, if CMPLNT_TO_TM exists)\
04	CMPLNT_TO_DT:	Ending date of occurrence for the reported event, if exact time of occurrence is unknown\
05	CMPLNT_TO_TM:	Ending time of occurrence for the reported event, if exact time of occurrence is unknown\
06	RPT_DT:	Date event was reported to police \
07	KY_CD:	Three digit offense classification code\
08	OFNS_DESC:	Description of offense corresponding with key code\
09	 PD_CD:	Three digit internal classification code (more granular than Key Code)\
10  PD_DESC:	Description of internal classification corresponding with PD code (more granular than Offense Description)\
11	CRM_ATPT_CPTD_CD:	Indicator of whether crime was successfully completed or attempted, but failed or was interrupted prematurely\
12	LAW_CAT_CD:	Level of offense: felony, misdemeanor, violation \
13	JURIS_DESC:	Jurisdiction responsible for incident. Either internal, like Police, Transit, and Housing; or external, like Correction, Port Authority, etc.\
14	BORO_NM:	The name of the borough in which the incident occurred\
15	ADDR_PCT_CD:	The precinct in which the incident occurred\
16	LOC_OF_OCCUR_DESC:	Specific location of occurrence in or around the premises; inside, opposite of, front of, rear of\
17	PREM_TYP_DESC:	Specific description of premises; grocery store, residence, street, etc.\
18	PARKS_NM:	Name of NYC park, playground or greenspace of occurrence, if applicable (state parks are not included)\
19	HADEVELOPT:	Name of NYCHA housing development of occurrence, if applicable\
20	X_COORD_CD:	X-coordinate for New York State Plane Coordinate System, Long Island Zone, NAD 83, units feet (FIPS 3104)\
21	Y_COORD_CD:	Y-coordinate for New York State Plane Coordinate System, Long Island Zone, NAD 83, units feet (FIPS 3104)\
22	Latitude:	Latitude coordinate for Global Coordinate System, WGS 1984, decimal degrees (EPSG 4326) \
23	Longitude:	Longitude coordinate for Global Coordinate System, WGS 1984, decimal degrees (EPSG 4326)\



# Installation && Startup instructions
To set up the program, please follow the following steps:
1. Clone the repository
2. Export an environment variable called CSV_PATH with the path to the CSV that will be used as a data wareouse by the program.
3. Run the application with Maven (run "mvn spring-boot:run" from the command line while within the folder



# Usage

After running the program, the application exposes 4 endpoints for data manipulation:

## GET endpoints:
•	dataset/stats/total – responds with a JSON that displays the total number of offenses
•	dataset/stats/offenses – responds with a JSON that displays the number of offenses categorized by their classification code (KY_CD) 

## DELETE endpoints:
•	/dataset/${id} – used for deleting an offense from the database. The ID parameter should match the complaint number (CMPLNT_NUM) of the offense in question.

## POST endpoint:
•	/dataset – accepts a JSON that contains the complaint number (CMPLNT_NUM) and classification code (KY_CD) of a new complaint and adds it to the end of the list of offenses


# Contributing
Pull requests are welcome. For any changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.
