#!/usr/bin/ruby
#Result 1797

File.open('input.txt', 'r') do |f|

  i = 1
  floor = 0

  f.each_char do |c|
    if c == '('
      floor +=1
    elsif c == ')'
      floor -=1
    end

    if floor < 0
      puts i
      break
    end

    i +=1
  end
end
