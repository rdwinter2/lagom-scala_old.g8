#!/usr/bin/perl -l
use Data::Cuid qw(cuid slug);

# mv --force README.md README.md_old; cat README.md_old | ./untransclude.pl | ./transclude.pl > README.md

#my \$dt = `date --iso-8601=ns --universal | tr --delete '\n+,T:\n-'`;
while(<>) {
  chomp;
  my \$line = \$_;
  if (\$line =~ /^transclude::/) {
    \$line =~ /^transclude::(.*)::\[(.*)\]\$/;
    \$transcluded_file = \$1;
    # If file contains directory entries then search for that directory
    # then seach for the subdirectories and finally the file
    # Example:
    # find . -name api -type d -print0 | xargs -0 -I % find % -name HelloWorldService.scala
    # currently only one level of directory with the file
    my @real_file = split(/\//, \$transcluded_file);
    my \$real_file = `find . -name \${real_file[0]} -type d -print0 | xargs -0 -I % find % -name \${real_file[1]}`;
    my @transcluded_file = split(/\./, \$transcluded_file);
    my \$regexp = \$2;
    my \$id = cuid();
    print "<!--- transclude::\${transcluded_file}::[\${regexp}] \$id -->\n";
    print "```\${transcluded_file[1]}";
    if (\$regexp ne "") {
      # if \$regexp ends in { or ( then add the balancing group ending
      # if { then replace it with ({(?:[^{}]++|(?1))*})
      # if ( then replace it with (\((?:[^()]++|(?1))*\))
      \$regexp = ".*" . \$regexp;
      my \$last = substr \$regexp, -1;
      #print \$last;
      my \$z = substr \$regexp, -1, 1, "({(?:[^{}]++|(?1))*})" if (\$last eq "{");
      my \$z = substr \$regexp, -1, 1, "(\\((?:[^()]++|(?1))*\\))" if (\$last eq "(");
      #print \$regexp;
      my \$data = `perl -0777 -ne 'print \\\${^MATCH} if /\$regexp/' \$real_file`;
      print \$data;
    } else {
      open(DATA, "<\$real_file");
      while(<DATA>) {
        chomp;
        print "\$_";
      }
      close DATA;
    }
    print "```\n";
    print "<!--- transclude \$id -->";
  } else {
    print  "\$line";
  }
}

#my \$file = \$ARGV[0];
#my \$dt = `date --iso-8601=ns --universal | tr --delete '\n+,T:\n-'`;
#open(DATA,"<\$file") or die "Can't open file \$file";
#my @file = split(/\./, \$file);
#my \$fileout = "\${file[0]}\${dt}.\${file[1]}";
#print "\$fileout\n";
#open(DATAOUT,">\$fileout") or die "Can't open file \$fileout";
#while(<DATA>) {
#  chomp;
#  if (\$_ =~ /^transclude::(.*)[(.*)]\$/) {
#    \$transcluded_file = \$1;
#    \$regexp = \$2;
#    print DATAOUT "<!---transcluded file \$transcluded_file with regular expression \$regexp -->\n";
#  } else {
#    print DATAOUT "\$_\n";
#  }
#}
#close DATA;
#close DATAOUT;
#my \$dt = `date --iso-8601=ns --universal | tr --delete '\n+,T:\n-'`;
#print "README\${dt}.md";
#my \$cp = `cp README.md README\${dt}.md`;

#`mv --force READMEtranscluded.md`
#
#

#@lines = <DATA>;
#close(DATA);
#
#while (<>) {
#  chomp;
#
#}
#my \$id   = cuid();          # cjg0i57uu0000ng9lwvds8vb3
#my \$slug = slug();          # uv1nlmi
#print "\$id\n";
#
#
#open(DATA, "<file.txt") or die "Couldn't open file file.txt, \$!";
#while(<DATA>) {
#   print "\$_";
#}
#
#close(DATA) || die "Couldn't close file properly";