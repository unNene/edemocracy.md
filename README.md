# edemocracy.md
Parties and Deputies parser for e-democracy.md

This code allows to get parsed Parties and Deputies from e-democracy.md website. It's a website from Republic of Moldova, I used
this code for another project, it's free now.

<h3>Examples:</h3>
<h4>Get Parties by Elections URL</h4>
    String url = "http://www.e-democracy.md/elections/parliamentary/2014/opponents/";
    Elections e2014 = new Elections(url);
    
<h4>Getting Party by short title</h4>
    Partid pldm = e2014.getPartidByShortTitle("PLDM");
<h4>Getting deputies from Party</h4>
    pldm.getDeputati()
