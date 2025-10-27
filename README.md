# Project Title:Implementing Access-Controlled Use Cases in a Digital University System
INFO5100 Group Assignment repo

1️⃣ Project Title

Digital University Information System with Role-Based Access Control

2️⃣ Team Information
Name	        	Role	      Responsibilities
Shi Haojiang		Admin Role	Admin dashboard, Person/User Management,
Zhou Bingjie		Student Role	Registration UI, Transcript, Tuition Payment,
Gao Xiyue		    Faculty Role	Grade Management, Performance Reports,
Liu Xuanli  	  Registrar Role	Registrar UI, Course Offering Management,

✅ Includes names + roles + NUID — per requirement

3️⃣ Project Overview

This project implements a digital university system that supports:

🎯 Objective

Provide separate secure work areas for Admin, Faculty, Student, and Registrar roles

Support course offerings, enrollment, academic performance & financial management

✨ Key Features

Authentication + Authorization (Role-Based Access Control)

Course creation & scheduling

Student registration & drop

Tuition billing & financial reconciliation

Transcript & GPA calculation

Grading & course performance analytics

Clean UI using CardLayout

4️⃣ Installation & Setup Instructions
✅ Prerequisites
Software	Version
Java JDK	17+ recommended
IDE	NetBeans 16
Build	Ant (default with NetBeans)
✅ Setup Steps
Open project in NetBeans
Clean and Build project
Run src/UserInterface/WorkAreas/Main/MainJFrame.java


Login page will load automatically ✅

5️⃣ Authentication & Access Control
🔐 Authentication

Login with username + password

Verified via UserAccountDirectory.AuthenticateUser()

Failed login remains in login panel

🔒 Role-based Access
Role	Authorized Work Area
Admin	User + Department + Course Master Data
Faculty	Grading & Student performance
Student	Registration, Transcript, Tuition
Registrar	Course offering + Enrollment admin + Finance

Unauthorized access → UI hides controls + panels ✅

6️⃣ Features Implemented
Role	Functions	Owner
Registrar ✅	Create course offering, assign faculty, enrollment admin, scheduling, tuition revenue reports	Liu Xuanli
Admin	User/Department CRUD	Shi haojiang
Student	Register/drop courses, transcript, tuition  Zhou Bingjie	
Faculty	Submit grades, view student performance	Teammate Gao Xiyue

✅ All functions fully mapped to role-permissions

7️⃣ Usage Instructions

🔑 Sample Login Accounts:

Role	Username	Password
Admin	admin	****
Registrar	registrar	****
Faculty	fac1	pass1
Student	stu1	pass1
Example Workflows

✔ Registrar → Open Course Offering → Add seats → View financials
✔ Student → Register → Tuition auto-calculated
✔ Faculty → Assign grades → GPA auto-refresh
✔ Admin → Manage accounts and people

8️⃣ Testing Guide

✅ Verify Authentication

Wrong password remains in login screen

Correct credentials → lands on correct WorkArea panel

✅ Verify Authorization

Registrar cannot view grading panel

Student cannot change course capacity

Sample test cases
Test ID	Action	Expected Result
T01	Login with registrar	Registrar UI only
T02	Registrar add course capacity	Student seats increase
T03	Student enroll	SeatAssignment created
T04	Faculty grade course	GPA recalculated
T05	Check revenue after add/drop	Fees updated
9️⃣ Challenges & Solutions
Challenge	Resolution
No built-in Registrar model	Added RegistrarProfile + Directory
ID validation failed new accounts	Standardized 7-digit rule
UI not refreshing dropdown/table	Added repopulation listeners
Merge conflicts	Resolved via PR review workflow
🔟 Future Enhancements

Database backend instead of memory storage

Hashing passwords for real security

Course prerequisite enforcement

Mobile-responsive UI with JavaFX

Export transcript to PDF

1️⃣1️⃣ Contribution Breakdown
Name	Coding	UI	Testing	Docs	PRs	% Work
Liu Xuanli	✅ 	✅ 	✅	✅ 	✅ 	25%
Shi Haojiang	✅	✅	✅	✅	✅	25%
Gao Xiyue		✅	✅	✅	✅	✅	25%
Liu Xuanli	✅	✅	✅	✅	✅	25%

Proof available via GitHub commit history ✅
✔ Ready for final packaging and submission ✅
