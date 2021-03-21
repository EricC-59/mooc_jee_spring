<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="web.Bag"%>
<%
Bag bag = (Bag) request.getAttribute("bag");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>Remplissez votre panier</title>
</head>
<body>
    <h1>Sac</h1>
    <%
    if (bag != null) {
        bag.print(out);
    }
    %>
    <form action='/exo103/bag' method='post'>
        <label for='ref'>Reférence&nbsp;:</label>
        <input type='text' name='ref'><br />
        <label for='qty'>Quantité&nbsp;:</label>
        <input type='text' name='qty'><br />
        <input type='submit'>
    </form>
</body>
</html>