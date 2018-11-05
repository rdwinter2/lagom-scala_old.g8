#!/usr/bin/env perl

# Read schema definition file

sub kabab {\$s = join('-', map {lc \$_} split(/(?=[A-Z])/, \$_[0]));}
sub snake {\$s = join('_', map {lc \$_} split(/(?=[A-Z])/, \$_[0]));}
sub word {\$s = join(' ', map {ucfirst \$_} split(/(?=[A-Z])/, \$_[0]));}
sub SNAKE {uc(snake(\$_[0]));}
sub camel {lcfirst \$_[0];}
sub Camel {ucfirst \$_[0];}

\$a1 = 'MyCamelCase';

my \$j = kabab(\$a1);
my \$k = snake(\$a1);
my \$i = camel(\$a1);
my \$m = Camel(\$a1);
my \$n = SNAKE(\$a1);
my \$w = word(\$a1);

print "\$a1 \$j \$k \$i \$m \$n \$w\n";