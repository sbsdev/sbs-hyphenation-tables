whitelist = whitelist_de_SBS.dic whitelist_de.dic

hyph_de_DE.dic: hyph_de_DE.orig.dic $(whitelist)
	cat $^ >$@

%.dic: %.txt
	./whitelist-to-patterns.sh <$< >$@

clean:
	rm -f hyph_de_DE.dic $(whitelist)

install: hyph_de_DE.dic hyph_de_OLDSPELL.dic
	install $^ $(DESTDIR)/usr/share/hyphen
