all: hyph_de_DE.dic hyph_de_OLDSPELL.dic

hyph_de_DE.dic: hyph_de_DE.orig.dic whitelist_de_SBS.dic whitelist_de.dic
	cat $^ >$@

hyph_de_OLDSPELL.dic: hyph_de_OLDSPELL.orig.dic whitelist_de_OLDSPELL.dic
	cat $^ >$@

%.dic: %.txt
	./whitelist-to-patterns.sh <$< >$@

clean:
	rm -f hyph_de_DE.dic hyph_de_OLDSPELL.dic whitelist_de_SBS.dic whitelist_de.dic whitelist_de_OLDSPELL.dic

install: hyph_de_DE.dic hyph_de_OLDSPELL.dic
	install -m 644 $^ $(DESTDIR)/usr/share/hyphen
