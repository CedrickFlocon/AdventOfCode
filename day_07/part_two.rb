#!/usr/bin/ruby
# Result 14134

require './operation'

first_letter = 'a'
$found_value = Hash.new()

def find_equation(letter)
  equation = File.readlines('input.txt').select { |l| l =~ /-> \b#{letter}\b/ }[0]
  action = equation.scan(/\b[A-Z]+\b/)[0]
  var = equation.scan(/(?!#{letter})\b[a-z]+\b/)

  if var.size == 0 # Assignation
    return equation.scan(/\d+/)[0].to_i
  else # Operation
    values = []
    var.each { |unknown|
      unless $found_value.has_key?(unknown)
        $found_value[unknown] = find_equation(unknown).to_i
      end
      values << $found_value[unknown]
    }

    # Shift value
    if equation.scan(/\d+/)[0].to_i > 0
      values << equation.scan(/\d+/)[0].to_i
    end

    return Operation.new(action, values).compute
  end
end

b = find_equation(first_letter)
$found_value = Hash.new()
$found_value['b'] = b

puts find_equation(first_letter).to_s
