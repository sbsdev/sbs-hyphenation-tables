sbs-hyphenation-tables
======================

German hyphenation tables used by SBS.

The tables are modified versions of the German tables provided by [LibreOffice][libreoffice]
(v4.0, `hyph_de_DE.dic`) and [OpenOffice.org][openoffice] (v3.2, `hyph_de_OLDSPELL.dic`).
They are enhanced with a list of exception words.

Create Debian package
---------------------

    mvn clean package

Install
-------

    dpkg -i target/*.deb

This will install the following hyphenation tables into /usr/share/hyphen:

 * hyph_de_DE.dic
 * hyph_de_DE_OLDSPELL.dic

License
-------

TODO

[openoffice]: http://svn.services.openoffice.org/ooo/tags/OOO320_m9/dictionaries/de_DE
[libreoffice]: http://cgit.freedesktop.org/libreoffice/dictionaries/tree/de?id=90bab4eb8a66b43783fbee9ee89166165ccb1ceb
