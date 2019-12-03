<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="flights" scope="request" type="java.util.List<airlines.entities.flights.Flight>"/>
<html>
<head>
    <title>All flights</title>
</head>
<body>
<p>Welcome to Admin Home Page</p>
<h1>All flights</h1>
<table cellpadding="4">
    <thead align="left">
    <tr>
        <th>Id</th>
        <th>Flight</th>
        <th>Status</th>
        <th>Date</th>
        <th>Departure_time</th>
        <th>From</th>
        <th>To</th>
        <th>Aircraft</th>
        <th>Crew</th>
    </tr>
    </thead>

    <tbody>

    <c:forEach items="${flights}" var="flight">
        <tr>
            <td> ${flight.id}</td>
            <td> ${flight.flightName.flightName}</td>
            <td> ${flight.statusId.value}</td>
            <td> ${flight.date}</td>
            <td> ${flight.flightName.departureTime}</td>
            <td> ${flight.flightName.departureAirport.value}</td>
            <td> ${flight.flightName.arriveAirport.value}</td>
            <td> ${flight.plane.plainRegNumber}</td>
            <td> ${flight.crewMembers.size()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<h3>Enter flight id for edit</h3>
<form method="post" action="/flights">
    <label>
        Flight id  <input type="text" name="flightId" autofocus="true">
    </label>
    <input type="submit" value="Edit" disabled="true">
</body>
</html>
