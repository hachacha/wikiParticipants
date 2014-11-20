/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wikipediansbynumberofedits_en;

/**
 *
 * @author wiki
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
 
abstract class WikipediansPrinter {
 
	private PrintWriter writer = null;
 
	public PrintWriter getWriter() {
		return writer;
	}
 
	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}
 
	private Date beginTimestamp = null;
	private Date endTimestamp = null;
 
	public Date getBeginTimestamp() {
		return beginTimestamp;
	}
 
	public void setBeginTimestamp(Date beginTimestamp) {
		this.beginTimestamp = beginTimestamp;
	}
 
	public Date getEndTimestamp() {
		return endTimestamp;
	}
 
	public void setEndTimestamp(Date endTimestamp) {
		this.endTimestamp = endTimestamp;
	}
 
	private int totalEdits = 0;
 
	public void setTotalEdits(int totalEdits) {
		this.totalEdits = totalEdits;
	}
 
	public int getTotalEdits() {
		return totalEdits;
	}
 
	private int totalEditsInPeriod = 0;
 
	public void setTotalEditsInPeriod(int totalEditsInPeriod) {
		this.totalEditsInPeriod = totalEditsInPeriod;
	}
 
	protected int getTargetTotalEdits() {
		return totalEditsInPeriod;
	}
 
	public void print(User[] users, UserGroups userGroups, int limit) {
		try {
			printHeader();
			Arrays.sort(users, createComparator());
			writer.print("{| class=\"wikitable" + getSortable() + "\"");
			writer.println();
			writer.print("! " + getTableHeader());
			writer.println();
			int rank = 0;
			int prevCount = 0;
			int sameRank = 0;
			int totalEditsByListedUsers = 0;
			int numberOfListedEditors = 0;
			for (User user : users) {
                            if(user.getEdits()==0){
                                    break;
                                }
				final String group = getGroup(user, userGroups.group(user.getId()));
				final String groupText = (group.equals("") ? "" : " (" + group + ")");
				final String rankText;
				if (!group.equals(UserGroups.BOT)) {
					if (rank == 0) {
						rank++;
						sameRank = 1;
					} else if (getTargetEdits(user) < prevCount) {
						rank += sameRank;
						sameRank = 1;
					} else {
						sameRank++;
					}
					rankText = Integer.toString(rank);
					numberOfListedEditors++;
					totalEditsByListedUsers += getTargetEdits(user);
					prevCount = getTargetEdits(user);
				} else {
					rankText = "";
				}
				if (rank > limit) {
					break;
				}
//				writer.print("|-");
//				writer.println();
//				writer.print("| " + rankText);
//				writer.print(" || ");
				processAnonymous(user);
//				if (user.getId() == 0) {
//					writer.print("[[" + getSpecialText() + ":Contributions/" + user.getText() + "|" + user.getText() + "]]");
//				} else {
//					writer.print("[[" + getUserText() + ":" + user.getText() + "|" + user.getText() + "]]");
//				}
//				writer.print(groupText);
//				printEdits(user);
//				writer.println();
                                String the_user = user.getText();
                                writer.print(the_user+"\n");
                                
 
			}
//			writer.print("|}");
//			writer.println();
			System.err.println("This list of " + limit + " editors represents " + totalEditsByListedUsers + " total edits,"
					+ " with an average of " + (int)(totalEditsByListedUsers / numberOfListedEditors) + " per editor.");
			System.err.println("This accounts for "
					+ new DecimalFormat("#0.0").format(((float)totalEditsByListedUsers / (float)getTargetTotalEdits()) * 100) + "%"
					+ " of the " + getTargetTotalEdits() + " total edits made to the Wikipedia.");
		} finally {
			writer.flush();
			System.err.flush();
		}
	}
 
	protected abstract int getTargetEdits(User user);
 
	protected abstract String getTableHeader();
 
	protected abstract String getSpecialText();
 
	protected abstract String getUserText();
 
	protected abstract Comparator<User> createComparator();
 
	protected void printHeader() {
		return;
	}
 
	protected abstract void printEdits(User user);
 
	protected void processAnonymous(User user) {
		return;
	}
 
	protected String getGroup(User user, String group) {
		return group;
	}
 
	protected final String SORTABLE = " sortable";
 
	protected String getSortable() {
		return "";
	}
 
}
