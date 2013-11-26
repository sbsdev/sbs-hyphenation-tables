sbs-hyphenation-tables
======================

German hyphenation tables used by SBS.

The tables are modified versions of the German tables provided by [OpenOffice.org][ooo].
They are enhanced with a list of exception words.

Create Debian package
---------------------

    mvn clean package

Install
-------

    dpkg -i target/*.deb

This will install the following hyphenation tables into /usr/share/hyphen:

 * hyph_de_DE.dic
 * hyph_de_DE_OLDSPELL

License
-------

TODO


[ooo]: http://svn.services.openoffice.org/ooo/tags/OOO320_m9/dictionaries/de_DE
