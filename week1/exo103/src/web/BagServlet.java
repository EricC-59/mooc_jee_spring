package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/bag")
public class BagServlet extends HttpServlet {
    private static final long serialVersionUID = -8971569105872527873L;

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Bag myBag = getBag(req);

        res.setContentType("text/html;charset=UTF-8");

        PrintWriter out = res.getWriter();
        out.write("<html>\n");
        out.write(" <body>\n");

        myBag.print(out);

        out.write("     <form action='/exo103/bag' method='post'>\n");
        out.write("         <label for='ref'>Reférence&nbsp;:</label>\n");
        out.write("         <input type='text' name='ref'><br/>\n");
        out.write("         <label for='qty'>Quantité&nbsp;:</label>\n");
        out.write("         <input type='text' name='qty'><br/>\n");
        out.write("         <input type='submit'>\n");
        out.write("     </form>\n");
        out.write(" </body>\n");
        out.write("</html>\n");

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        String ref = req.getParameter("ref");
        String qty = req.getParameter("qty");

        PrintWriter out = res.getWriter();
        out.write("<html>\n");
        out.write(" <body>\n");

        if (ref == null || qty == null || ref.isBlank() || qty.isBlank()) {
            res.setStatus(400);
            out.write("<p>Erreur. Champ non renseigné.</p>");
            out.write("<br/>");
        } else {
            try {
                Bag myBag = getBag(req);
                myBag.setItem(ref, Integer.parseInt(qty));
                HttpSession ses = req.getSession();
                ses.setAttribute("myBag", myBag);
                myBag.print(out);
                res.sendRedirect("/exo103/bag");
            } catch (NumberFormatException e) {
                res.setStatus(400);
                out.write("<p>Erreur. Quantité non numérique.</p>");
                out.write("<br/>");
            }
        }

        out.write(String.format("<p>Référence&nbsp;: %s<br/>", ref));
        out.write(String.format("Quantité&nbsp;: %s</p>", qty));

        out.write(" </body>\n");
        out.write("</html>\n");

    }

    private Bag getBag(HttpServletRequest req) {
        HttpSession ses = req.getSession();
        Bag myBag = (Bag) ses.getAttribute("myBag");
        if (myBag == null) {
            myBag = new Bag();
        }

        return myBag;
    }

}
