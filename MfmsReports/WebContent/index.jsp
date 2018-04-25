<html>
<head><h1>Towngas e-Patrol System</h1>
</head>
<body>
	<h2>Towngas e-Patrol System WebServices</h2>
	<h3>Patrol Routine</h3>
	<p>
		<a
			href="http://localhost:8080/MfmsReports/rest/generatePatrolReportWs/test">test
			generatePatrolReportWs</a>
	</p>
	<p>
		<a
			href="http://localhost:8080/MfmsReports/rest/generatePatrolReportWs/requestPatrolRoutineJson?siteKey=2&routeKeyList=1,2,3,4&resultStartDate=19700101_000000&resultEndDate=20180101_235900">requestPatrolRoutineJson</a><br />
		sample query string:<br />
		http://localhost:8080/MfmsReports/rest/generatePatrolReportWs/requestPatrolRoutineJson?siteKey=2&amp;routeKeyList=1,2,3,4&amp;resultStartDate=19700101_000000&amp;resultEndDate=20180101_235900<br />
	</p>
	<p>
		<a
			href="http://localhost:8080/MfmsReports/rest/generatePatrolReportWs/requestPatrolRoutineExcel?siteKey=2&routeKeyList=1,2,3,4&resultStartDate=19700101_000000&resultEndDate=20180101_235900">requestPatrolRoutineExcel</a><br />
		http://localhost:8080/MfmsReports/rest/generatePatrolReportWs/requestPatrolRoutineExcel?siteKey=2&amp;routeKeyList=1,2,3,4&amp;resultStartDate=19700101_000000&amp;resultEndDate=20180101_235900<br />
	</p>
	<h3>Inspection Report</h3>
	<p>
		<a
			href="http://localhost:8080/MfmsReports/rest/generateInspectionReportWs/test">test
			generateInspectionReportWs</a><br />
	</p>
	<p>
		<a
			href="http://localhost:8080/MfmsReports/rest/generateInspectionReportWs/requestInspectionJson?siteKey=5&routeKeyList=1,2,3,4&resultStartDate=19700101_000000&resultEndDate=20180101_235900">requestInspectionJson (Sample 1)</a><br />
		sample query string:<br />
		http://localhost:8080/MfmsReports/rest/generatePatrolReportWs/requestInspectionJson?siteKey=5&amp;routeKeyList=1,2,3,4&amp;resultStartDate=19700101_000000&amp;resultEndDate=20180101_235900<br />
				<a
			href="http://localhost:8080/MfmsReports/rest/generateInspectionReportWs/requestInspectionJson?siteKey=5">requestInspectionJson (Sample 2)</a><br />
		sample query string:<br />
		http://localhost:8080/MfmsReports/rest/generatePatrolReportWs/requestInspectionJson?siteKey=5<br />				
	</p>
	<p>
		<a
			href="http://localhost:8080/MfmsReports/rest/generateInspectionReportWs/requestInspectionExcel?siteKey=5&routeKeyList=1,2,3,4&resultStartDate=19700101_000000&resultEndDate=20180101_235900">requestInspectionExcel (Sample 1)</a><br />
		sample query string:<br />
		http://localhost:8080/MfmsReports/rest/generatePatrolReportWs/requestInspectionExcel?siteKey=5&amp;routeKeyList=1,2,3,4&amp;resultStartDate=19700101_000000&amp;resultEndDate=20180101_235900<br />		
				<a
			href="http://localhost:8080/MfmsReports/rest/generateInspectionReportWs/requestInspectionExcel?siteKey=5">requestInspectionExcel (Sample 2)</a><br />
		sample query string:<br />
		http://localhost:8080/MfmsReports/rest/generatePatrolReportWs/requestInspectionExcel?siteKey=5<br />			
	</p>
</body>
</html>