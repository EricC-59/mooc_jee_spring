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
    private static final long serialVersionUID = -897156910587252773L;

    private static final String MY_BAG = "myBag";

    public static final String JSP_VIEW = "/WEB-INF/bag.jsp";

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setAttribute("bag", req.getSession().getAttribute(MY_BAG));
        req.getRequestDispatcher(JSP_VIEW).forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        String ref = req.getParameter("ref");
        String qty = req.getParameter("qty");

        PrintWriter out = res.getWriter();

        if (ref == null || qty == null || ref.isBlank() || qty.isBlank()) {
            res.setStatus(400);
            out.write("<p>Erreur. Champ non renseigné.</p>");
            out.write("<br/>");
        } else {
            try {
                Bag myBag = getBag(req);
                myBag.setItem(ref, Integer.parseInt(qty));
                HttpSession ses = req.getSession();
                ses.setAttribute(MY_BAG, myBag);
                myBag.print(out);
                res.sendRedirect("/exo103/bag");
            } catch (NumberFormatException e) {
                res.setStatus(400);
                out.write("<p>Erreur. Quantité non numérique.</p>");
                out.write("<br/>");
            }
        }

        out.write(" </body>\n");
        out.write("</html>\n");

    }

    private Bag getBag(HttpServletRequest req) {
        HttpSession ses = req.getSession();
        Bag myBag = (Bag) ses.getAttribute(MY_BAG);
        if (myBag == null) {
            myBag = new Bag();
        }

        return myBag;
    }

}
