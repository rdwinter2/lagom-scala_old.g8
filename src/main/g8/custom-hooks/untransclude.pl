#!/usr/bin/perl -l
use Data::Cuid qw(cuid slug);

#my \$dt = `date --iso-8601=ns --universal | tr --delete '\n+,T:\n-'`;
my \$id = " ";
my \$skip = 0;
my \$re = "";
#print "::\${id}::\n";
#if (\$id =~ /^ *\$/) {
#  print "::\${id}::\n";
#}
while(<>) {
  chomp;
  my \$line = \$_;
  #print "::\${line}::\n";
  if (\$line =~ /^<!--- transclude::.*\$/) {
    \$line =~ /^<!--- transclude::(.*)::\[(.*)\] (.*) -->\$/;
    \$file = \$1;
    \$regularexp = \$2;
    \$id = \$3;
    print "transclude::\${file}::[\${regularexp}]";
    \$re = "^<!--- transclude \${id} .*\\$";
    #print "::\${re}::\n";
    \$skip = 1;
  }
  if (\$skip == 1) {
    if (\$line =~ /\$re/) {
      \$id = "last";
      \$skip = 0;
    }
  }
  #print "::\${id}::\n";
  if (\$id =~ /^ *\$/) {
    print "\$line";
  }
  if (\$id eq "last") {
    \$id = "";
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