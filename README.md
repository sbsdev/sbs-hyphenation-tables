# sbs-hyphenation-tables

German hyphenation tables used by [Swiss Library for the Blind, Visually Impaired and Print Disabled][sbs].

The tables are modified versions of the German tables provided by [OpenOffice.org][ooo].
They are enhanced with a list of exception words.

## Create Debian package

```sh
mvn clean package
```

## Release

```sh
mvn release:clean release:prepare -DdryRun=true
mvn release:clean release:prepare release:perform
```

after that make sure the artifacts are pushed to Maven Central by
closing and releasing them in the
[Sonatype Nexus Repository Manager](https://oss.sonatype.org/#stagingRepositories).


## Install

```sh
dpkg -i target/*.deb
```

This will install the following hyphenation tables into `/usr/share/hyphen`:

- `hyph_de_DE.dic`
- `hyph_de_DE_OLDSPELL`

## Authors

- [Bert Frees][frees]
- [Christian Egli][egli]

## License

Copyright 2013-2014 [Swiss Library for the Blind, Visually Impaired and Print Disabled][sbs]

Licensed under [GNU Lesser General Public License][] as published by
the Free Software Foundation, either version 3 of the License, or (at
your option) any later version.

[frees]: https://github.com/bertfrees
[egli]: https://github.com/egli
[sbs]: http://www.sbs.ch/
[GNU Lesser General Public License]: http://www.gnu.org/licenses/lgpl.html
[ooo]: http://svn.services.openoffice.org/ooo/tags/OOO320_m9/dictionaries/de_DE
