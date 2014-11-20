This program will output all individual names who have made at least one edit to a wikipedia article to two files. One will only contain usernames and the other will only contain ip-addresses.

Follow the instructions on here to make sure you have all appropriate files:
	http://en.wikipedia.org/wiki/Wikipedia:List_of_Wikipedians_by_number_of_edits/How_to_generate_the_lists
You need to have the file: enwiki-latest-stub-meta-history.xml.gz 
	from: http://dumps.wikimedia.org/enwiki/latest/

run this command from the command line while in the dist/ folder

java -classpath /Users/wiki/repos/WikipediansByNumberOfEdits_en/build/classes/wikipediansbynumberofedits_en -Xmx6400m -Dbegin.date=2010-01-01 -Dend.date=2014-10-31 -jar /Users/wiki/repos/WikipediansByNumberOfEdits_en/dist/WikipediansByNumberOfEdits_en.jar enwiki-latest-stub-meta-history.xml.gz enwiki-latest-user_groups.sql.gz

it will take a few hours to complete but should output to two text files usernames.txt and ip-and-other.txt
